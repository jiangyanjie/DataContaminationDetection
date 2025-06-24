/*
 * Copyright 2012-2013 Sebastien Zurfluh
 * 
 * This file is part of "Parcours".
 * 
 * "Parcours" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "Parcours" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with "Parcours".  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.sebastienzurfluh.swissmuseum.core.client.control.eventbus;

/**
 * This is the superclass for events using {@link EventBus}.
 * @author Sebastien Zurfluh
 *
 */
public abstract class AbstractEvent {
	
	/**
	 * This enum lists all possible UI (View) event types. 
	 */
	public enum EventType {
		/**
		 *  Fired when a new page is requested.
		 */
		PAGE_CHANGE_REQUEST,
		/*
		 *  Fired when a page change is approved.
		 */
		PAGE_CHANGE_EVENT,
		/**
		 * Fired when the page appearance is changed.
		 */
		PAGE_MODIFIED_EVENT,
		/**
		 *  Fired when a widget finished loading.
		 */
		WIDGET_LOADED_EVENT,
		/**
		 *  Fired when a resource is needed.
		 */
		RESOURCE_REQUEST,
		/**
		 * Fired when the user creates/modifies/deletes a model element
		 */
		ACTION,
		/**
		 * Fired when the user is requesting the means to take an {@code Action} on the DB.
		 */
		INTENT;
	}

	/**
	 * An event has one only event type which you can fetch with this method.
	 * @return get the event's type
	 */
	public abstract EventType getType();

}
