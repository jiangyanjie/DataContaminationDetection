package ru.seyseich.domain.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ru.seyseich.domain.entities.BaseEntity;
import ru.seyseich.domain.repositories.IRepository;
import ru.seyseich.utils.ExceptionUtil;

public abstract class AbstractService< T extends BaseEntity > implements IService< T >
{
	protected abstract IRepository< T > getRepository( );
	protected abstract void copyProperties( T object, T dbObject );
	
	@Transactional
	public T create( T object )
	{
		return getRepository( ).save( object );
	}

	@Transactional( rollbackFor = Exception.class )
	public T delete( int id ) 
		throws Exception
	{
		T object = findById( id );
		if ( null == object )
			ExceptionUtil.throwException( "Объект %s с ID = %s не нйден.", getEntityClass( ).getSimpleName( ), id );
		
		getRepository( ).delete( object );
		
		return object;
	}

	@Transactional
	public List< T > findAll( )
	{
		return getRepository( ).findAll( );
	}

	@Transactional( rollbackFor = Exception.class )
	public T update( T object ) 
		throws Exception
	{
		if ( null == object )
			ExceptionUtil.throwException( "Нельзя обновить объект null." );
		
		Integer id = object.getId( );
		T obj = findById( id );
		
		if ( null == obj )
			ExceptionUtil.throwException( "Объект %s с ID = %s не найден.", getEntityClass( ).getSimpleName( ), id );
		
		copyProperties( object, obj );
		getRepository( ).save( obj );
		
		return obj;
	}

	@Transactional
	public T findById( int id )
	{
		return getRepository( ).findOne( id );
	}
}
