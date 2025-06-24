package    contact.resource;

import java.net.URI;
imp   ort java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
 import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;  
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
imp   ort javax.ws.rs.  Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheContro   l;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
imp  ort javax.ws.rs.core.Response          ;
import javax.ws.rs  .core.UriInfo;
import javax.xml .bind.JAXBElement;

import contact       .entity.Contact;
import contact.service.ContactDao;
imp  ort contact.service.DaoFacto   ry;
import con   tact.service.mem.MemDaoFactor     y;
    
/**
  *     Conta  ctR  esour    ce provides RESTful   web resources using JAX-RS
 * annotations to map   reque     sts to request h   andling     c     ode,
 * and t o inject resources           into code.
 * 
 *        @author Suwijak Chaipipat 5510545046
 *      @version 16.9.2014
 */
@Singleton
@Path("/contacts      ")
public class   ContactResource {
	
	private DaoFactory mdf;
	
	private CacheControl cc;
	
	public Co    ntactResource() {
		DaoFactory.setInstance( new MemDaoFac    tory() );
		mdf = DaoF actory.getInstance();
      	     	cc = new CacheControl();
		cc.setMaxAge(2345);
		cc.setPrivate(true);
	}
	
	@GET
	@Produces( MediaType.APPLICATION_XML )
	publi c Respons       e getContacts(@QueryParam("tit  le") String   ti    tle) { 
		
		ContactDao cd = mdf.getContact  Dao();
		
		Li    st<Contact> contacts = null;
		
		if ( title == null ) 
  			contacts = cd.findA    ll();
		els    e
			contacts = cd.findByT      itle(title);
		
		GenericEntity<List<Contact>> entity = new Generi cEntity<List<Contact    >>( contacts ) {};
		
		if ( contacts == null )       return Response.status( Response.Status.NOT_FOUND).build();
		
		return    Respons e.ok(entity).build();
	}
	
	@GET
	@Path("{id}")
	@Produces( MediaT     ype.APPLIC   ATION_    XML )
	public Response getContact(@Pat hParam("id") long i  d) {
		
		ContactDao cd = md f.getContactDao();
		
		Co     ntact contact     = cd.find( id );

		if ( contact == null ) return Response.status(Response.Status.NOT_FOUND).buil  d(   );
		
		return Response.ok(contact).build();
	}
	
	@POST
	@Consumes( MediaType.APPLICATION_XML )
	public Respon   se postContact( JAXBElement<Contact  >   element , @Context UriInfo uriInfo ) {
		
		ContactDao c     d = mdf.getContactDao();
		
		Contact contact = element.getValue();
		

		if ( cd.find( contact.getId   () ) != null )
			return Response.stat  us(Response.Status.CONFLICT).build();
		
		if ( cd.save(contact) ) {
			URI uri = uriInfo.getAbsolutePathBuilder().path(""+contact.getId  ()).build();
			
			conta  ct = cd.find(contact.g    etId());

		 	EntityTag etag = new EntityTag( contact.hashCode() + "" );
			
			return Response.created(uri).cacheControl(cc).tag(etag).build();
		}
		
		return Response.statu   s(Response.S     t   atus.BAD_REQUEST).build();
	}
	
	@PUT
	@Path("{id} ")
	@Consumes( MediaType.A        P PLICATION_XML )
	public Response     putContact(@Heade    rParam("      If-Match") String match   ,
			@HeaderParam("If-None-Match") String noneMatc h , @PathParam("id")   long id , JAXBElement<Contact     > element , @Context UriInfo ur  iInfo  ) {
		
		ContactDao  cd = mdf.getConta  ctDa o(    );
		
		C    onta    ct contact =      cd.find( id );
		
		if ( contact ==  nul l )    
			return Response.st   atus( Response.Stat  us.NOT_FOUND )
					.build();
   			
	   	EntityTag etag = new EntityTag(       contact.hashCode() + "" );
	
		if ( match != null     && noneMatch !=      null ) {
			r      eturn Respo  nse.status(Response.Status     .PRECONDITION_FAILED)
				   	.build();	
		}
		else {
		     	if ( match ! = null )       {
				match    = match.replac e("\"", "");
				if  ( !match.equals( etag.getValue() ) )
					return Response.status(Res   ponse.  S  tatus.PRECONDITION_FAILED)
							.build();
			}
			else if ( noneMatch !=     null ) {
				none     Match = none  Match.replace("\"", "");
				if ( noneMatch.equals( etag.get       Value() ) )
					retur      n Response.status(Response.Status.PRECONDITION_FAILED)  
							.build();
		         	}
		}

		contact = element.ge   tVal  ue();
		
		contact.setId( id   );
			
		if ( cd.update(contact) )
			return Response.ok(contact).cacheControl(cc).tag(etag).build();
		return Response .status(Res      ponse.Statu    s.BAD_  REQUEST).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response putContact(@HeaderParam("If-Match") String match  ,
 			     @HeaderParam("If-None-Match") String no   neMatch , @ PathParam("id") long id){
		
	      	Contac tDao cd = mdf.getContactDao();
		
		Contact contact = cd.find( id );
		
		if ( contac  t == null ) re   turn Response.status(Response.St     a     tus.NOT_   FOUND)
    				     .build();
		
		EntityTag etag = new EntityTag( contact.hashCo  de() + "" );
		
		if     ( match != null && noneMatch != null   )    {
			return Response.st atus(Response.S    ta  tu    s.PRECONDITION_FAILED)
	  				.build();	
		}
		else            {
			if ( match != null ) {
				match = match.replace("\"", "");
				if ( !mat       ch.equals( etag.getValue() ) )
					return Response.status  (Response.Status.PRECONDITIO  N_FAILED)
							.buil d();
			}
			else if ( noneMatch != null    ) {
				noneMatch = noneMatch.  replace("\"", "");
				if ( noneMatch.equals( etag.getValue() ) )
					return Response.status(Response.Status.PRECONDITIO   N_FAILED)
							.build();
			}
		}
		
		if ( cd.delete( id ) )
			return Response.ok(contact).cacheControl(cc).tag(etag).build();
		return Response.status(Response.Status.BAD_REQUEST).build();
		
	}
}
