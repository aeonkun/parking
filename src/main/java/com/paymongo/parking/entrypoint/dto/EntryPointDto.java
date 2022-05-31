package com.paymongo.parking.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data transfer object class which contains the entry point name.
 *
 */
public class EntryPointDto {

	private final String name;

	/**
	 * Creates the {@link EntryPointDto}.
	 * 
	 * @param name of the {@link EntryPointDto}
	 */
	@JsonCreator
	public EntryPointDto(@JsonProperty("name") String name) {
		this.name = name;
	}

	/**
	 * Gets the name of the {@link EntryPointDto}
	 * 
	 * @return name of the {@link EntryPointDto}
	 */
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		EntryPointDto other = (EntryPointDto) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
