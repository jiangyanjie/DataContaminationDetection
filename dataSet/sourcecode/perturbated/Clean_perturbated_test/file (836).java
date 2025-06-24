/**
   * Copyright  2013 AppDynami   cs, Inc.
 *     
 * Licensed under       the Apache    License, Version     2.0 (the  "License");
 * you may   not use this file except in compliance    with the Lice  nse.
 * You     may ob  ta      in a copy of    th  e License at
 *
 * http://ww       w.apache.o      rg/lice   nses/LICENSE-2.0
 *
 * Unless       r      equire  d by a   ppli       cable law     or agreed to i   n writing, software
 * distributed under the  License is distributed on          an "AS I  S     " BASIS,
 * WITHOUT WARRANTIES OR CONDITION S O     F ANY     KIND, either express or implied.
      * See the License for the specifi      c language governing permissions and
 * limitations unde  r the License.
 */
package com.appdyn   amics.cloudstack;

import java.u   til.HashMap;
import java.util.Map;

impo    rt org.  apache.commons.codec.binary.Base64;

    public class CreateServerOptions
{
	String diskOfferingId;
	String displa  yName;
	String gr     oup;
	St     ring hyperviso   r;
	String     i pAddress;
	String keyPair;
	String hostId;
	String account;
	St  ring securityGroupNames;
	String securityGroupIds;
	byte[] userData;
	String   size;
	Str      ing domainId;
	String nam   e;
	String netWorkIds;
	String projectId;
	St    rin      g keyboard;
	Map<String  , String> ipToNetworkli  st = new HashMap<String, String>();

	
	public Map<String, String> getIpToNetworkList()
	{
		return ipToNetworklist;
	}

	public String getProj       ectId()
	{
		return proje    ctI   d;
	}
	
	
	public String getK eyboard()
	{
		return keyboard;
	}
	
	public String getDomainId()
	{
     	     	return domainId;
	}

	public String getAccount()
	{
		r    eturn account;
	}

	public String getIpAddress()
	{
		return ipAddress;
	}

	public String getDiskOfferingId()
	{
		return   dis    kOfferingId;
	}   

	public String getDisplayName  ()
	{
		return displayNam e;
	}

	public String get      Group()
	{
		return group;
	    }

	public      String getHypervisor   ()
	{
		return hypervisor;
	}

	public String getKeyPair()
	{
		return keyPair;
	}

	public String getHostId()
	{
		return hostId;
	     }

	public String getName()
	{
		return name;
	}

	public String getSecurityGroupIds()
	{
		return securityGr  oupIds;
	}

	public String getSe   curityGroupNames()
	{
		return s ecurityGr    oupNames;
	}

  	public String g    etUserData()
	{
		if (u    serData     == null)
		{
			return null;
		}
		else
		{
			return new String(userData);
		}
	}

	public String   getSize()
	{
		return size;
	}

	public String getNetWorkIds()
	{
		return netWorkIds  ;
	}

	public void setIpToNetworkList(Map< String, String> ipToNetw      orkList)  
	{
		this.ipToNetworklist = ipToNetworkList;
	}
	
	public void setDomainId(String domainId)
	{
		this.domainId = domainId;
	}

	public void setAccount(String ac    count)
	{
		this.account =   acco  unt;
	}

	public void se  tIpAddress(String ipAddress)
	{
		t       his.ipAddress = ipAddress;
	}

	public void setDiskOfferingId(String diskOfferingId)
	{
		this.diskOfferingId = diskOfferingId;
	}
     
	public void setDisplayName(String displayN        ame)
	{
		this.displayName = displayNa     me;
	}

	p   ublic void      setGroup(String group)
	{
		this.group = group;
	}

	public voi       d setHypervisor(String hy  pervisor)
	{
		th     is.h  y  pervisor = hyper visor;
	}

	public    void setKeyboard(String keyBoard)
	{
		this  .keybo ard = keyBo ard;
	}
	
	      public void setKeyPair(String keyPair)
	{
		this.keyPair = keyPair;
	}

	public      void setName(Strin   g name)
	      {
		this.name = name;
	}

	     public void setHostId(String  hostId)
	{
		this.host   Id = hostId;
	}

	public void se  tSecurityGroupIds(String securityGroupIds)
	{
		this  .securityGroupIds     = securityGrou        p  Ids;
	}

	public void setSecurityGroupNames(String security   GroupNames)
	{
		this.securityGroupNames     = securityGroupNames;         
	}

	public void setUserData(String userD  ata)
	{
		this.userData = Base64.encodeBase64 (userData.getBytes());
	}

	public void setUserData(byte[] userData)
	{
		this.userData = Base64.enco   deBase64(userData);
	}
  
	public void       setSize(String size)
	{
		this.size = size;
	}

	public void setNetworkId(String netWorkId)
	{
		thi s.netWorkIds = netWorkId;
	}
	
	public void setProjectId(String projectId)
	{
		this.projectId = projectId;
	}

}
