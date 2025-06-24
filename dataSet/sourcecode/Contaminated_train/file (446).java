/**
 * Copyright (C) 2014 Sappenin Inc. (developers@sappenin.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.sappenin.ms.activitystrea.v1;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Preconditions;

/**
 * An ActivityStrea.ms Activity.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Activity implements Iconable, Serializable
{
	// Required for Pipeline integration
	private static final long serialVersionUID = -2026567605480696645L;

	// ///////////////////
	// ActivityStrea.ms Properties
	// ///////////////////

	// Provides a permanent, universally unique identifier for the object in the form of an absolute IRI [RFC3987]. An
	// object SHOULD contain a single id property. If an object does not contain an id property, consumers MAY use the
	// value of the url property as a less-reliable, non-unique identifier.
	private String id;

	// An IRI [RFC3987] identifying a resource providing an HTML representation of the activity. An activity MAY contain
	// a url property.
	private String url;

	// [RFC3339] date-time The date and time at which the activity was published. An activity MUST contain a published
	// property.
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	// See http://cokere.com/RFC3339Date.txt
	private DateTime published;

	// [RFC3339] date-time The date and time at which a previously published activity has been modified. An Activity MAY
	// contain an updated property.
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
	private DateTime updated;

	// The basic model for an Activity provides properties that allow the expression of who (actor) performed the
	// action, what (object) was acted upon, the action (verb) that was taken, and to what the action was
	// directed (target).

	// Describes the entity that performed the activity. An activity MUST contain one actor property whose value is a
	// single Object.
	private com.sappenin.ms.activitystrea.v1.Object actor;

	// Identifies the action that the activity describes. An activity SHOULD contain a verb property whose value is a
	// JSON String that is non-empty and matches either the "isegment-nz-nc" or the "IRI" production in [RFC3339]. Note
	// that the use of a relative reference other than a simple name is not allowed. If the verb is not specified, or if
	// the value is null, the verb is assumed to be "post".
	private String verb;

	// Describes the primary object of the activity. An activity SHOULD contain an object property whose value is a
	// single Object. If the object property is not contained, the primary object of the activity MAY be implied by
	// context. For instance, in the activity, "John saved a movie to his wishlist", the object of the activity is
	// "movie". Likewise, for the activity "John added a tag to Idea 'XYZ'", the object of the activity is "tag".
	// However, for "John tagged the Idea 'Buy More Stuff' with 'Walmart'", then the object is the Idea, and the target
	// is 'Walmart'.
	private com.sappenin.ms.activitystrea.v1.Object object;

	// Describes the target of the activity. The precise meaning of the activity's target is dependent on the activities
	// verb, but will often be the object the English preposition "to". For instance, in the activity,
	// "John saved a movie to his wishlist", the target of the activity is "wishlist". Likewise, for the activity
	// "John added a tag to Idea 'XYZ'", the target of the activity is the "idea". However, for the activity
	// "John tagged the Idea 'Buy More Stuff' with 'Walmart'", then the object is the Idea, and the target
	// is 'Walmart'. The activity target MUST NOT be used to identity an indirect object that is not a target of the
	// activity. An activity MAY contain a target property whose value is a single Object.
	private com.sappenin.ms.activitystrea.v1.Object target;

	// Describes the application that generated the activity. An activity MAY contain a generator property whose value
	// is a single Object.
	private com.sappenin.ms.activitystrea.v1.Object generator;

	// Describes the application that published the activity. Note that this is not necessarily the same entity that
	// generated the activity. An activity MAY contain a provider property whose value is a single Object.
	private com.sappenin.ms.activitystrea.v1.Object provider;

	// Description of a resource providing a visual representation of the object, intended for human consumption. The
	// image SHOULD have an aspect ratio of one (horizontal) to one (final vertical) and SHOULD be suitable for
	// presentation at a small size. An activity MAY have an icon property.
	private MediaLink icon;

	// Natural-language title or headline for the activity encoded as a single JSON String containing HTML markup. An
	// activity MAY contain a title property.
	private String title;

	// Natural-language description of the activity encoded as a single JSON String containing HTML markup. Visual
	// elements such as thumbnail images MAY be included. An activity MAY contain a content property.
	private String content;

	// An HTML Encoded title or headline for the activity encoded as a single JSON String without HTML markup. An
	// activity MAY contain a title property.
	private String titleWithoutHtml;

	// The additional context property allows the Activity to further include information about why a particular action
	// occurred by providing details about the context within which a particular Activity was performed. The value of
	// the context property is an Object of any objectType. The meaning of the context property is only defined when
	// used within an Activity object. See http://activitystrea.ms/specs/json/schema/activity-schema.html and
	// https://github.com/activitystreams/activity-schema/blob/master/activity-schema.md
	private com.sappenin.ms.activitystrea.v1.Object context;

	/**
	 * No Args Constructor
	 * 
	 * @deprecated Exists only to satisfy libraries (like Jackson) which require a no-args constructor for
	 *             serialization/deserialization. Will likely be removed in a future release and replaced with Jackson
	 *             Creator mechanisms (for one example, see here:
	 *             "http://www.cowtowncoder.com/blog/archives/2010/08/entry_409.html").
	 */
	@Deprecated
	public Activity()
	{
	}

	/**
	 * Required args constructor.
	 * 
	 * @param id The id, generally a URL, URI, or IRI.
	 */
	public Activity(String id)
	{
		this.id = id;
	}

	/**
	 * Copy constructor to help maintain immutable characteristics.
	 * 
	 * @param activity An {@link Activity} source object.
	 */
	public Activity(final Activity activity)
	{
		Preconditions.checkNotNull(activity);

		this.setId(activity.getId());
		this.setUrl(activity.getUrl());
		this.setPublished(new DateTime(activity.getPublished()));
		this.setUpdated(new DateTime(activity.getUpdated()));
		this.setVerb(activity.getVerb());
		this.setTitle(activity.getTitle());
		this.setTitleWithoutHtml(activity.getTitleWithoutHtml());
		this.setContent(activity.getContent());

		if (activity.getActor() != null)
		{
			this.setActor(new com.sappenin.ms.activitystrea.v1.Object(activity.getActor()));
		}

		if (activity.getObject() != null)
		{
			this.setObject(new com.sappenin.ms.activitystrea.v1.Object(activity.getObject()));
		}

		if (activity.getTarget() != null)
		{
			this.setTarget(new com.sappenin.ms.activitystrea.v1.Object(activity.getTarget()));
		}

		if (activity.getGenerator() != null)
		{
			this.setGenerator(new com.sappenin.ms.activitystrea.v1.Object(activity.getGenerator()));
		}

		if (activity.getProvider() != null)
		{
			this.setProvider(new com.sappenin.ms.activitystrea.v1.Object(activity.getProvider()));
		}

		if (activity.getIcon() != null)
		{
			this.setIcon(new MediaLink(activity.getIcon()));
		}

		if (activity.getContext() != null)
		{
			this.setContext(new com.sappenin.ms.activitystrea.v1.Object(activity.getContext()));
		}

	}
}
