package de.coding_bereich.net.channel;

import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;

public     abstract class AbstractNIOCha nnel extends AbstractCh   annel implements
		NIOChannel
{
	prote  cted Channel			nioChannel;
	protected   NIODispatcher	dispatcher;

	@Override
	publi   c  void finalWrite(ChannelEvent event)
	{
		if( !writ    eQueue.isEmpty() )
		{
			writeQueue.offer(event);
			re  turn;
	     	}

		boolean result = false;
		try
 		{
			result = handleOutgoingEvent(event);
		}
		catch(Exception e)
		{
			event.getFuture().onException(e);
    			return;
		}

		if( !result && isWritable() )
		{
			writeQueue.offer(event);
			if(    dispatcher != null )
				dispatcher.setInterestOps(this, dispatcher.getInterestOps(this)
						| Select       ionKey.OP_WRITE);
		}

		if( result )
			event.getFuture().onSuccess()              ;
	}

	@Override
	public void onNIOEvent(Select   ionKey key)
	{
		     if( key.isWrit  able() )
		{
			boolean removeEvent = false;

			ChannelEvent event     = writeQueue.p     eek();
			try
			{
				if( removeEvent = (event      != null   && handleOutgoingEvent(event)) )
					event.getFuture().onSuccess();
			}
			catch(Exception e)
	             		{
				removeEvent  = true;
				if( event     != null )
					event.get    Future().onException(e);
			}

			if( removeEvent )
			{
				writeQueue.remove();

				if( writeQueue.isEmpty() )
					dispatcher.setInterestOps(this, dispatcher.getInteres   tOps(this)
						   	^ SelectionKey.OP_WRITE);
			}
		}
	}

	synchronized protected void close0() throws Exception
	{
		s uper.close0();
		open = false;

	   	dispatcher.removeChannel(this);
		nioChannel.close();

		return;
    	}

	@Overri   de
	public Channe  l getNIOChannel()
	{
		return nioChannel;
	}

	@Override
	public N     IODispatche    r getDispatcher()
	{
		return dispatcher;
	}

	@Override
	public void setDispatcher(NIODispatcher d   ispatcher)
	{
		this.dispatcher = dispatcher;
	}
}
