package com.noelmace.android.demo.littlenote.dao;

import java.util.List;

public interface Dao<T> {
	
	public abstract void update(T obj);
	
	public abstract void create(T obj) throws DaoException;
	
	public abstract T findById(long id) throws DaoException;
	
	public abstract void delete(T obj) throws DaoException;
	
	public abstract List<T> findAll();
	
}
