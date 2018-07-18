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

		Product p1 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("UCB Men's Cotton T-Shirt").description("Black Round Neck Full Sleeve T-Shirt")
				.category(mensCategory).price(1500F).quantity(5).size(Arrays.asList("L")).brand("UCB").build());

		Product p2 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("U.S. Polo Assn. Cotton Polo").description("U.S. Polo Assn. Men's Solid Regular Fit Cotton Polo")
				.category(mensCategory).price(1345F).quantity(5).size(Arrays.asList("S")).brand("U.S Polo").build());

		Product p3 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("WROGN T-shirt").description("WROGN Men Grey & Rust Brown Striped Polo Collar T-shirt")
				.category(mensCategory).price(3000F).quantity(5).size(Arrays.asList("M")).brand("WROGN").build());

		Product p4 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("United Colors of Benetton Men").description("United Colors of Benetton Men Beige & Blue Striped Polo Collar T-shirt")
				.category(mensCategory).price(1500F).quantity(5).size(Arrays.asList("S")).brand("United Colors").build());

		Product p5 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Puma T-shirt").description("Puma Men's Cotton Polo")
				.category(mensCategory).price(719F).quantity(5).size(Arrays.asList("XL")).brand("Puma").build());

		Product p6 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Reebok T-shirt").description("Reebok Men's Cotton Polo")
				.category(mensCategory).price(1500F).quantity(5).size(Arrays.asList("M")).brand("Reebok").build());

		Product p7 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Adidas T-shirt").description("Adidas Men's Cotton Polo")
				.category(mensCategory).price(719F).quantity(5).size(Arrays.asList("SL")).brand("Adidas").build());

		Product p8 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Fila T-shirt").description("Fila Men's Cotton Polo")
				.category(mensCategory).price(1500F).quantity(5).size(Arrays.asList("XXL")).brand("Fila").build());

		mensCategory.setProducts(Arrays.asList(p1, p2, p3 ,p4 ,p5, p6, p7, p8));
		categoryRepository.save(mensCategory);

		Product p9= productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("W Ethnics").description("W Women Orange & Beige Printed Straight Kurta")
				.category(womensCategory).price(1250F).quantity(5).size(Arrays.asList("L")).brand("W").build());

		Product p10= productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("AKS Dress").description("Biba Women Green Solid Kurta with Churidar & Dupatta")
				.category(womensCategory).price(2400F).quantity(5).size(Arrays.asList("S")).brand("AKS").build());

		Product p11= productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("AKS Dress").description("AKS Women Green & White Printed Maxi Dress")
				.category(womensCategory).price(1125F).quantity(5).size(Arrays.asList("S")).brand("AKS").build());

		Product p12= productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("W For Women").description("W Women Turquoise Blue & Coral Orange Printed Straight Kurta")
				.category(womensCategory).price(3056F).quantity(5).size(Arrays.asList("L")).brand("W").build());

		Product p13 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("HERE&NOW Top").description("HERE&NOW Women Black Lightweight Floral Print Cold Shoulder Top")
				.category(womensCategory).price(1455F).quantity(5).size(Arrays.asList("L")).brand("HERE&NOW Top").build());

		Product p14 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("THERE&THEN Top").description("THERE&THEN  Women Black Lightweight Floral Print Cold Shoulder Top")
				.category(womensCategory).price(1500F).quantity(5).size(Arrays.asList("XL")).brand("THERE&THEN Top").build());

		Product p15 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("THEN&THERE Top").description("THEN&THERE Women White Lightweight Floral Print Cold Shoulder Top")
				.category(womensCategory).price(1455F).quantity(5).size(Arrays.asList("L")).brand("THEN&THERE  Top").build());

		Product p16 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("NOW Top").description("NOW Women Yellow Lightweight Floral Print Cold Shoulder Top")
				.category(womensCategory).price(1500F).quantity(5).size(Arrays.asList("SL")).brand("NOW Top").build());

		womensCategory.setProducts(Arrays.asList(p9, p10, p11 ,p12 ,p13, p14, p15 ,p16));
		categoryRepository.save(womensCategory);

		Product p17 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("CUTECUMBER Girl's Dress").description("CUTECUMBER Girls Pink Self Design Fit and Flare Dress")
				.category(kidsCategory).price(1260F).quantity(5).size(Arrays.asList("L")).brand("CUTECUMBER").build());

		Product p18 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("StyleStone Girl's Dress").description("StyleStone Girls Blue & Off-White Printed A-Line Dress")
				.category(kidsCategory).price(5045F).quantity(5).size(Arrays.asList("S")).brand("StyleStone").build());

		Product p19 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("My Little Lambs Girl's Dress").description("My Little Lambs Girls Beige Printed Empire Dress")
				.category(kidsCategory).price(2600F).quantity(5).size(Arrays.asList("M")).brand("My Little Lambs").build());

		Product p20 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("LilPicks Boys").description("LilPicks Boys Maroon & Navy Blue Solid T-shirt with Shorts")
				.category(kidsCategory).price(1500F).quantity(5).size(Arrays.asList("L")).brand("LilPicks").build());

		Product p21 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Mothercare Girls Cardigan").description("Mothercare Girls Yellow Self-Striped Cardigan")
				.category(kidsCategory).price(3015F).quantity(5).size(Arrays.asList("S")).brand("Mothercare").build());

		Product p22 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Fathercare Girls Cardigan").description("Fathercare Girls Yellow Self-Striped Cardigan")
				.category(kidsCategory).price(3015F).quantity(5).size(Arrays.asList("L")).brand("Fathercare").build());

		Product p23 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Dadcare Girls Cardigan").description("Dadcare Girls White Self-Striped Cardigan")
				.category(kidsCategory).price(2600F).quantity(5).size(Arrays.asList("SL")).brand("Dadcare").build());

		Product p24 = productRepository.save(Product.builder().productId(String.valueOf(++productId)).productName("Grannycare Girls Cardigan").description("Grannycare Girls Pink Self-Striped Cardigan")
				.category(kidsCategory).price(3015F).quantity(5).size(Arrays.asList("XXL")).brand("Mothercare").build());

		kidsCategory.setProducts(Arrays.asList(p17, p18, p19 ,p20 ,p21, p22, p23, p24));
		categoryRepository.save(kidsCategory);

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
