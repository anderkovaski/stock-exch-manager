package stock.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import stock.domain.enumerator.OrderTypeEnum;

@Entity
@Table(name = "orders")
public class Order implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private OrderTypeEnum type;
	
	@OneToOne
	private Stock stock;
	
	private Integer amount;
	
	private Date createdAt;
	
	private Date runOn;

	@ManyToOne(fetch = FetchType.EAGER)	
	private Order purchaseOrder;	
	
	@JsonIgnore
	@OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
	private Set<Order> salesOrder = new HashSet<>();
	
	public Order( ) {
		
	}

	public Order(Long id, OrderTypeEnum type, Stock stock, Integer amount, Date runOn, Order purchaseOrder) {
		super();
		this.id = id;
		this.type = type;
		this.stock = stock;
		this.amount = amount;
		this.createdAt = new Date();
		this.runOn = runOn;
		this.purchaseOrder = purchaseOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderTypeEnum getType() {
		return type;
	}

	public void setType(OrderTypeEnum type) {
		this.type = type;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getRunOn() {
		return runOn;
	}

	public void setRunOn(Date runOn) {
		this.runOn = runOn;
	}

	public Order getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(Order purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public Set<Order> getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(Set<Order> salesOrder) {
		this.salesOrder = salesOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
