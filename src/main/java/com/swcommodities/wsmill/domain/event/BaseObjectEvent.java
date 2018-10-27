package com.swcommodities.wsmill.domain.event;

import java.util.Objects;

import com.swcommodities.wsmill.domain.event.si.SICostUpdateObjectEvent;
import com.swcommodities.wsmill.domain.model.RefAware;

public class BaseObjectEvent<T extends RefAware> {
	private T item;

    public BaseObjectEvent(T item) {
		// TODO Auto-generated constructor stub
    		this.item = item;
	}

	public T getItem() {
		return item;
	}

	public void setItem(T item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "BaseObjectEvent %s [ref=" + item.getRefNumber() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
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
		BaseObjectEvent other = (BaseObjectEvent) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}
	
	
	
	
	
    
}
