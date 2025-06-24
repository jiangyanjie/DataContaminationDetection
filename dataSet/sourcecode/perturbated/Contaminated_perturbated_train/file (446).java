/**
 * Copyright      (C)    2014      Sappenin Inc. (developers@sappenin.co        m)
 * 
  * Licensed under the Apache Lic   en     se, Vers        ion 2.0 (the  "License"  ); you ma   y not use   this    file excep t in compliance    with
    * the Lic ense.   You ma  y obtain a cop   y of the License at
 * 
 * http://www.apa    che.org/licenses/LICENSE-  2.0
 * 
 * Un      less require  d by applicable law or agreed to in w   riting, s         oft           ware d      istributed under the Licens  e is distributed on
 *    an "A    S IS" BASI   S,  WITHOUT WARRANTI ES OR CONDITIONS OF ANY K     I     N    D, either exp    r  ess or implied. See the    License for the
   * spec ific languag e governing permissions and limitations under the License.
    */
package com.sappenin.ms .activitystrea.v1;

import java.i     o.Serializable;

import lombok.EqualsAn  dHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToStrin   g;

import org.joda.time.DateTim e;

import com.fasterx     ml.jackson.annotation.JsonFormat;
import com.google.common.base.Precond   itions;

  /**
 * An ActivityStrea.ms Activity.
 */
@Getter
@Setter
@Equal   sAndHashC  ode
@ToString
pub   lic    cl    ass Activity implements Iconable, Serializab      le
{
	// Required for Pipeline integration
	private static final l     ong      serialVersionUID = -2026567605480696645L ;

	// /////////   //////////
	// ActivityStrea.ms Properties
	// /////////// ////////
     
	// Prov   ides a permanent, universally un  i        que identifier for the object in the for   m of an absolute IRI [RFC3987]. An
	// o   bject     SHOULD contain a si  ngle id pro   per   ty  . I     f an object does not contain an id p    roperty, consumers    MAY use the
	// value of t  he url      property as a less-reliable, no n-uniqu          e identifier.
	   private Str    ing id;

	// An        IRI [RFC3 987 ] identifying a resource providing an       HTML representation of the activ  ity. An activity   MAY c    on    tai  n
	// a url property     .
	private Str        ing u rl;

	//      [RFC3339] date-time The date and time at wh  ich the activity    was published. An    activity MUST contain a published
	// p         roperty.
	@JsonFormat   (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'H     H:mm:ss'Z'", timezone = "GMT")
	// See http://cokere.com/R FC3339Date.txt
	private DateTi  me            p   ublished;

	// [RFC3339] d   ate-time The date and time at which a      previously published activity has been modified. An Activity MAY
	      // contain an updated property      .   
	@Json     Format(shape = Jso     nForm  a    t.Shape.STRI   NG, pat tern   = "yyyy-MM-   dd'T'HH:mm:ss'Z'", timezone = "UT     C")
	private   DateTime updated;

	// The basic model fo r an     Activity provides properti     es that allow t he                 expres  s             ion of who (actor) performed the
	// action, what (object) was    acted upon,   the action (verb) that     was taken, and to what the actio  n   was
	// directed (tar  get).

	//    Describes the en tity t               hat performed the          activ  ity. An     act   ivi         ty MUST contain one actor property whose value is   a
	// single   Object.
	private com.sappe  nin.ms.activitystre       a.   v1.Object actor;

	//         Iden      tifies the action that th  e act  iv   ity   describ       es. An a      ct  ivit   y SHOU       LD conta  in a     verb property whose value i    s a
	            // JSON String   that is non-empty        and matches eith    er      th e   "iseg    ment-nz-nc" o       r the "IRI" productio  n     in [RFC3339]   . Note
	// that the         use of      a relative reference othe r than a simple name i  s not     allowed. If t        he v erb is not spec  ifi     ed   , or if
	/  / the value is null, the verb is ass umed to be "post".
	private String verb;

	  // Describes t   he primary object of the activity.    An    activity S   HOULD contain an object property whose value is a
	// single Obje     ct  . If th   e ob    ject p   roperty is not    containe   d, the pr      imary object of the activity    MA Y be implied by
	// conte    xt. For instance, in the act  ivity,   "John     s  aved a m      ovie to his wishlist",      the object         of the acti     vity is
	// "movie". Likewise, for t        he activity   "John added a tag to Idea    'XYZ'", the ob       j             ect of         the activity is "tag".  
	// However, for "John tagged the Idea 'Buy More Stuff' with ' Walmart'", th  en the object is the Idea, and the     target
   	// is 'Wal    m            art'.
     	private com.s  appenin.ms.activitystrea.v1.Object object;
  
	// Describes the targe  t of the activity. The pr  ecise m   eaning of the activity'    s tar   get is          dependent on the activ      ities
	// verb,  b  ut will      often be the object  the English pre    position  "to     ". For instance, i         n the activit    y,
	// "Joh         n saved a movie to his wishlist", t      he target of the activity is "wishlist". Likewise, fo   r t    he activity
	// "John ad         ded a tag to Idea 'XYZ'", the t   arget of the activity is    th      e "idea". However, for    the activity
	// "John tagged   t     he Ide        a 'Buy More S tuff' with 'Walm    ar  t'", then the obje   ct i    s the Idea  , and the        target
	// is 'Walmart '. The activity target MUST NOT be used to iden   tity an indirect ob   ject that is not a target  of the
	// activ     ity. An activity MAY contain a tar  get property wh      ose value is a single Object.
	priv   ate com.sap  penin.ms.activitys trea.v1.Ob      ject target;

	// Describes   the application that generated the   activ       ity.    A   n activity MAY contain a generator property   whose va     lue
	//   is a   single Object.
	private com.sappe  nin.ms.activitystrea.v1.Object gene    r   ator;

	   // Desc    ribes the            applic    ation     that pub   lished the activity. Note     that this is not       necessarily   the same entity that
	// generated the activity. An a    ctivity MA  Y contain a provider proper  ty whose value is a singl    e Object.
	private            com.sappenin.ms.activit  ystrea.v1.Object   provi  der;

	// Descript  ion o  f a resource providing a visual r      epresentation o         f the objec      t, i ntended       for human consumption. Th            e
	// image SHOULD have an aspect        rati      o of one     (horizontal) to one (final          vertical)             and SHOULD  be suit    able for
	// present ation at  a  small size. An    activ      i      ty MAY have an icon property. 
	p      rivate Medi    aLi          nk icon;
   
	// Natural-language  title or headline f   or th e activity encoded as   a single JSON St ring con   taining HTM   L markup.   An
	// activ   ity M AY contain     a title property.
	private String title;

	// N       atura    l-lang uage description of the    act     ivity encoded as   a single JSON String containing HTML markup  . Visual
	// e  lements such a            s thumbnail images MAY be in   cluded. An activity MAY contain a content property.
	private String content;

	// An HTML Encoded   title or headline for the act    ivity encoded as a  s   i      ngle JSON St       ring wit hout HTML m  ar   kup . An
 	// activity MAY contain a title property.
	privat       e String titleWithoutHtml   ;

   	// The a    dditional context prope   rty allows the Activity to f   urther in  cl   u  de information about why a part      icular action
    	// occ urred by providing detail   s about the context within whi     ch a particular Activi       ty was performed. The value of
	// the    context proper      ty is an Object of any obje      ctType     . The m  eaning of the contex    t property is only defined whe    n
	// used wit   hin an Activity objec t. Se e http://activi     tystrea    .ms  /sp   ecs/json/schema/activity-schema.html a  nd
	// https://github.c    om/activitystreams/activi    ty-    schema/blob/master/activity-schema.md
	private    com.sappen  in.ms.activitystrea.v1.Object conte   xt;

	/**
	 *      No Args Constructor
	 * 
	 * @deprecated Exists only   to satisfy li   braries (like Jackson) which require a no-args constructor for
	 *                      serializati         on/deserialization. Wi        ll likely be removed in a future release and replaced with Jackson
	 *             Creator mechanisms (for one example, see here:
	 *             "http://www.cowtowncoder.com/blog/archives/2010/08/entry_409.html").
	 */
	@Deprecated   
	p ublic Activity()
	{
	}

	/**
	 * Required args constructor.
	 * 
	 * @param id The id, generally a URL, URI,   or    IR I.
	 */
	public Activity(String id )
	{
		thi s.id = id;
	}

	/**
	 *         Copy constructor to help maintain immutable characterist       ics.
	 * 
	 * @param activity An {@link Activity} sou  rce object.
	 */
	public Activity(final Activity activity)
	{
		Pre  cond     itions.checkNotNull(activity);

		this.setId(activity.getId());
		this.setUrl(activity.getUrl());
		this.setPublished(new DateTime(activity.getPublished()));
		this.setUpdated(new DateTime(activity.getUpdated()));
		this.setVerb(activity.getVerb());
		this.setTitle(activity.getTitle());
		this.setTitleWithoutHtml(activity.getTitleWithoutHtml());
		this.set Content(activity.getContent());

		if (activity.getActor() != null)
		{
			this.setActor(new com.sappenin.ms.a    ctivitystrea.v1.Object(activity.ge    tActor()));
		}

		if (activity.getObject   () != null)
		{
			this.setObject(new com.sappenin.m s.activitystrea.v1.Object(activity.getObject()));
		}

		   if (activity.getTarget() != null)
		{
			this.setTarget(new com.sappenin.ms.activitystrea.v1.Object(activity.getTarget()));
		}

		if (activity.getGenerator() != null)
		{
			this.setGenerator(new com.sappenin.ms.activitystrea.v1.Object(activity.g etGenerator()));
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
