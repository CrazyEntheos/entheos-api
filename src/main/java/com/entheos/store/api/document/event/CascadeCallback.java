package com.entheos.store.api.document.event;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ReflectionUtils;

import com.entheos.store.api.annotation.CascadeSave;
import com.entheos.store.api.util.ObjectUtils;

public class CascadeCallback implements ReflectionUtils.FieldCallback {

	private static final Logger LOG = LoggerFactory.getLogger(CascadeCallback.class);
	
	private BeforeConvertEvent<Object> event;
	private MongoOperations mongoOperations;

	CascadeCallback(BeforeConvertEvent<Object> event, final MongoOperations mongoOperations) {
		this.event = event;
		this.mongoOperations = mongoOperations;
	}

	@Override
	public void doWith(final Field field) throws IllegalAccessException {
		ReflectionUtils.makeAccessible(field);

		if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
			final Object fieldValue = field.get(event.getSource());
			LOG.info("Event source: "+ event.getSource().getClass().getName());
			
			boolean isCollection = Collection.class.isAssignableFrom(field.getType());
			if (fieldValue != null) {
				if(LOG.isDebugEnabled()) {
					LOG.info("Target reference: "+ fieldValue.getClass().getName());
					LOG.debug("Target reference value: " + ObjectUtils.logObject(fieldValue));
				}

				if(fieldValue instanceof List) {
					LOG.debug("Collection "+fieldValue.getClass().getName());
					for(Object o: (List<?>)fieldValue) {
						checkAndSave(o, isCollection);
					}
				}else {
					LOG.debug("Object "+fieldValue.getClass().getName());
					checkAndSave(fieldValue, isCollection);
				}
			}
		}

	}

	protected void checkAndSave(Object fieldValue, boolean isCollection){
		final IdFieldCallback callback = new IdFieldCallback(fieldValue);
		ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
		try {
			LOG.debug(fieldValue.getClass().getSimpleName()+ "(Id: "+callback.getId()+ ")");
			if(!isCollection && callback.isIdFound() && StringUtils.isBlank(callback.getId().toString())) {
				mongoOperations.save(fieldValue);
			} else if (isCollection && callback.isIdFound() && StringUtils.isBlank(callback.getId().toString())){
				mongoOperations.save(fieldValue);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
}