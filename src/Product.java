import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
	@Id
	private Long id;
	private String name;
	private double price;
	
	public Product() {}
	
	public Product(Long i, String n, double p) {
		id = i;
		name = n;
		price = p;
	}
	public Long getId() {return id;}
	public String getName() {return name;}
	public double getPrice() {return price;}
}