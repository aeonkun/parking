package com.paymongo.parking.entrypoint.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entry point object which contains the id and name of the entry point.
 *
 */
@Entity
@Table(name = "entry_point")
public class EntryPoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;

	public EntryPoint() {
	}

	/**
	 * Creates the {@link EntryPoint} object.
	 * 
	 * @param id   of the {@link EntryPoint}
	 * @param name of the {@link EntryPoint}
	 */
	public EntryPoint(long id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Returns the id of the {@link EntryPoint}
	 * 
	 * @return id of the {@link EntryPoint}
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id of the {@link EntryPoint}
	 * 
	 * @param id of the {@link EntryPoint}
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the name of the {@link EntryPoint}
	 * 
	 * @return name of the {@link EntryPoint}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the {@link EntryPoint}
	 * 
	 * @param name of the {@link EntryPoint}
	 */
	public void setName(String name) {
		this.name = name;
	}

}
