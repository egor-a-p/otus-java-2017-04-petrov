package ru.otus.entity;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NamedQueries({
	@NamedQuery(name = "UserDataSet.readByName", query = "SELECT u FROM UserEntity u WHERE u.name = :name"),
	@NamedQuery(name = "UserDataSet.readAll", query = "SELECT u FROM UserEntity u")
})
public class UserEntity extends BaseEntity {

	@Column(name = "name", unique = true)
	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private AddressEntity userAddress;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private List<PhoneEntity> phones = new ArrayList<>();

	@Column(name = "p_data", nullable = false)
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private String password = StringUtils.EMPTY;

	public void setPassword(String password) {
		this.password = Base64.getEncoder().encodeToString(password.getBytes());
	}

	public String getPassword() {
		return new String(Base64.getDecoder().decode(password), Charset.forName("UTF-8"));
	}
}
