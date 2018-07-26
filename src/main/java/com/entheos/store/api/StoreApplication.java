package com.entheos.store.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.entheos.store.api.document.Category;
import com.entheos.store.api.document.Order;
import com.entheos.store.api.document.OrdersLineItem;
import com.entheos.store.api.document.Product;
import com.entheos.store.api.document.event.CascadeMongoEventListener;
import com.entheos.store.api.repository.CartRepository;
import com.entheos.store.api.repository.CategoryRepository;
import com.entheos.store.api.repository.CustomerRepository;
import com.entheos.store.api.repository.OrderRepository;
import com.entheos.store.api.repository.ProductRepository;
import com.entheos.store.api.repository.RepositoryPackage;
import com.entheos.store.api.util.ObjectUtils;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = RepositoryPackage.class)
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class StoreApplication implements CommandLineRunner{

	private static final Logger LOG = LoggerFactory.getLogger(StoreApplication.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private OrderRepository orderRepository;

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

	@Bean
	public CascadeMongoEventListener cascadingMongoEventListener() {
		return new CascadeMongoEventListener();
	}

	@Bean
	public CommonsRequestLoggingFilter logFilter() {
		CommonsRequestLoggingFilter filter
		= new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(10000);
		filter.setIncludeHeaders(true);
		filter.setIncludeClientInfo(true);
		return filter;
	}

	@Bean
	public LoggingEventListener  loggingEventListener() {
		return new LoggingEventListener();
	}

	@Override
	public void run(String... args) throws Exception {

		int productId = 1000000;
		int categoryId = 5000000;

		customerRepository.deleteAll();
		cartRepository.deleteAll();
		categoryRepository.deleteAll();
		productRepository.deleteAll();
		orderRepository.deleteAll();

		Category mensCategory = categoryRepository.insert(
				Category.builder().categoryId(String.valueOf(++categoryId)).categoryName("Mens").description("Mens wear").build());
		Category womensCategory = categoryRepository.insert(
				Category.builder().categoryId(String.valueOf(++categoryId)).categoryName("Womens").description("Womens wear").build());
		Category kidsCategory = categoryRepository.insert(
				Category.builder().categoryId(String.valueOf(++categoryId)).categoryName("Kids").description("Kids wear").build());
		Category newarrivalsCategory = categoryRepository.insert(
				Category.builder().categoryId(String.valueOf(++categoryId)).categoryName("NewArrivals").description("New Arrivals").build());

		Product p1 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Mebaz").description("Mebaz Kurtha")
				.category(mensCategory).price(1500F).quantity(5).size(Arrays.asList("L")).brand("UCB").build());

		Product p2 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("U.S. Polo Assn.").description("U.S. Polo Assn. Men's Cotton Shirt")
				.category(mensCategory).price(1345F).quantity(5).size(Arrays.asList("S")).brand("U.S Polo").build());

		Product p3 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("WROGN T-shirt").description("WROGN Men Grey Polo T-shirt")
				.category(mensCategory).price(3000F).quantity(5).size(Arrays.asList("M")).brand("WROGN").build());

		Product p4 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("VOI Jeans").description("VOI Jeans Blue")
				.category(mensCategory).price(1500F).quantity(5).size(Arrays.asList("S")).brand("United Colors").build());

		Product p5 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("ZARA").description("ZARA Men's Cotton Shirt")
				.category(mensCategory).price(719F).quantity(5).size(Arrays.asList("XL")).brand("Puma").build());

		Product p6 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Reebok T-shirt").description("Reebok Men's Cotton Polo")
				.category(mensCategory).price(1500F).quantity(5).size(Arrays.asList("M")).brand("Reebok").build());

		Product p7 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Cottonworld").description("Cottonworld Pant")
				.category(mensCategory).price(719F).quantity(5).size(Arrays.asList("SL")).brand("Adidas").build());

		Product p8 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Fila T-shirt").description("Fila Men's Cotton Polo")
				.category(mensCategory).price(1500F).quantity(5).size(Arrays.asList("XXL")).brand("Fila").build());

		mensCategory.setProducts(Arrays.asList(p1, p2, p3 ,p4 ,p5, p6, p7, p8));
		categoryRepository.save(mensCategory);

		Product p9= productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Mebaz").description("Mebaz Yellow Kurta")
				.category(womensCategory).price(1250F).quantity(5).size(Arrays.asList("L")).brand("W").build());

		Product p10= productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("W").description("W Women Red Kurta")
				.category(womensCategory).price(2400F).quantity(5).size(Arrays.asList("S")).brand("AKS").build());

		Product p11= productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("AND").description("AND Women Kurtha")
				.category(womensCategory).price(1125F).quantity(5).size(Arrays.asList("S")).brand("AKS").build());

		Product p12= productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("W For Women").description("W For Women Pink Dress")
				.category(womensCategory).price(3056F).quantity(5).size(Arrays.asList("L")).brand("W").build());

		Product p13 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Weavers India").description("Weavers India Dress")
				.category(womensCategory).price(1455F).quantity(5).size(Arrays.asList("L")).brand("HERE&NOW Top").build());

		Product p14 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Kalanjali").description("Kalanjali Yellow Material")
				.category(womensCategory).price(1500F).quantity(5).size(Arrays.asList("XL")).brand("THERE&THEN Top").build());

		Product p15 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Globaldesi").description("Globaldesi Grey Top")
				.category(womensCategory).price(1455F).quantity(5).size(Arrays.asList("L")).brand("THEN&THERE  Top").build());

		Product p16 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("AND").description("AND Yellow Top")
				.category(womensCategory).price(1500F).quantity(5).size(Arrays.asList("SL")).brand("NOW Top").build());

		womensCategory.setProducts(Arrays.asList(p9, p10, p11 ,p12 ,p13, p14, p15 ,p16));
		categoryRepository.save(womensCategory);

		/* Product p17 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("CUTECUMBER Girl's Dress").description("CUTECUMBER Girls Pink Self Design Fit and Flare Dress")
				.category(kidsCategory).price(1260F).quantity(5).size(Arrays.asList("L")).brand("CUTECUMBER").build()); */
		
		Product p17 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("UCB").description("UCB Black T Shirt Boys")
				.category(kidsCategory).price(1260F).quantity(5).size(Arrays.asList("L")).brand("CUTECUMBER").build());
		Product p18 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Mebaz").description("Mebaz Kurta Boys")
				.category(kidsCategory).price(5045F).quantity(5).size(Arrays.asList("S")).brand("StyleStone").build());

		Product p19 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Pepe Jeans").description("Pepe jeans T shirt Boys")
				.category(kidsCategory).price(2600F).quantity(5).size(Arrays.asList("M")).brand("My Little Lambs").build());

		Product p20 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Mebaz Girls").description("Mebaz Girls Jeans")
				.category(kidsCategory).price(1500F).quantity(5).size(Arrays.asList("L")).brand("LilPicks").build());

		Product p21 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Mothercare").description("Mothercare Girls Cardigan")
				.category(kidsCategory).price(3015F).quantity(5).size(Arrays.asList("S")).brand("Mothercare").build());

		Product p22 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Princess").description("Princess Girls Green Frock")
				.category(kidsCategory).price(3015F).quantity(5).size(Arrays.asList("L")).brand("Fathercare").build());

		Product p23 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Mebaz Boys").description("Mebaz Boys Kurtha")
				.category(kidsCategory).price(2600F).quantity(5).size(Arrays.asList("SL")).brand("Dadcare").build());

		Product p24 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("H&M").description("H&M Boys Shorts")
				.category(kidsCategory).price(3015F).quantity(5).size(Arrays.asList("L")).brand("Mothercare").build());

		kidsCategory.setProducts(Arrays.asList(p17, p18, p19 ,p20 ,p21, p22, p23, p24));
		categoryRepository.save(kidsCategory);
		
		Product p25 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Mothercare").description("Mothercare Girls Cardigan")
				.category(kidsCategory).price(3015F).quantity(5).size(Arrays.asList("S")).brand("Mothercare").build());

		Product p26 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Princess").description("Princess Girls Green Frock")
				.category(kidsCategory).price(3015F).quantity(5).size(Arrays.asList("L")).brand("Fathercare").build());

		Product p27 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Mebaz Boys").description("Mebaz Boys Kurtha")
				.category(kidsCategory).price(2600F).quantity(5).size(Arrays.asList("SL")).brand("Dadcare").build());

		Product p28 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("H&M").description("H&M Boys Shorts")
				.category(kidsCategory).price(3015F).quantity(5).size(Arrays.asList("L")).brand("Mothercare").build());
		
		newarrivalsCategory.setProducts(Arrays.asList(p25, p26, p27 ,p28));
		categoryRepository.save(newarrivalsCategory);

		Order order = new Order();

		List<OrdersLineItem> orderLineItems = new ArrayList<>();
		Float totalAmount = 0F;

		for (Category category : categoryRepository.findAll()) {
			LOG.debug(ObjectUtils.logObject(category));
		}
		for (Product product : productRepository.findAll()) {
			OrdersLineItem item = new OrdersLineItem();
			item.setProductId(product.getProductId());
			item.setQuantity(product.getQuantity());
			item.setSize(product.getSize().get(0));
			totalAmount = totalAmount + product.getPrice();
			orderLineItems.add(item);
			LOG.debug(ObjectUtils.logObject(product));
		}
		order.setOrderLineItems(orderLineItems);
		order.setTotalAmount(totalAmount);
		LOG.debug(ObjectUtils.logObject(orderRepository.insert(order)));
	}

}
