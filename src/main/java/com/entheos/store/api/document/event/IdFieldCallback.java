package com.entheos.store.api.document.event;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.util.ReflectionUtils;

import com.entheos.store.api.StoreApplication;


public class IdFieldCallback implements ReflectionUtils.FieldCallback {
    
	private static final Logger LOG = LoggerFactory.getLogger(StoreApplication.class);
	
	private boolean idFound;
	
	private Object id;
	
	private Object source;
	
	IdFieldCallback(final Object source){
		this.source = source;
	}
	
    @Override
    public void doWith(final Field field) throws IllegalAccessException {
    	LOG.debug("Class Type is: "+ source.getClass().getName()+ ",fieldType: "+ field.getType().getName());
        ReflectionUtils.makeAccessible(field);

        if (field.isAnnotationPresent(Id.class)) {
            idFound = true;
            id = field.get(source);
            LOG.debug("Class Type is:{}, @Id field:{}, value:{}", source.getClass().getName(),field, id);
        }
    }

    public boolean isIdFound() {
        return idFound;
    }
    
    public Object getId() {
        return id;
    }   
    
}