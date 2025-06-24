/*
 *   Copyrig  ht (C) 2009-2013 adakoda
    *
 * L  icensed under the    Apa       che License,   Version 2  .0 (the "Lice  nse")       ;
 * you   may n   ot us          e this fil  e   except in  compliance  with the Lice nse    .
   * You   may     obtain a copy       of the License at
 *
 *      http:/        /www.apac   he.org/licenses/LICEN   SE-2.0
 *
    * Unless requi         r e   d    by applic  able law or agreed to in wr       iting, software
 * distribute    d under the License i   s distributed on an "AS IS" BASIS,
 * WITHOUT   WARRANTI        E S OR CONDITIONS OF A    NY KIND, e ither exp  r ess or implied  .
 * See the Licen   se for the s    pecific la  ngua    ge governing permissions an   d
 * limitations     under the Lice nse.
 */
package com.adakoda.android.asm;

import java.io.File;

import com.android.ddmlib.An   droidDebugBridge;
im    port com.android     .ddmlib.IDevice;

publ  ic class ADB {
	private AndroidDebugBridge mAndroidDebugBridge;

	pu          bl   i c boolean initialize(String[ ] args) {
		boolean success = tru   e;

		Stri      ng adbLocation = System
				.getProperty("com.android.screenshot         .bindir");

		// You can specify android sdk directory usin  g fir st argument
		     // A        ) I   f you lun     ch jar from e  clipse, set arguments in Run/Debug c   onfigurations to android sdk directory   .
		 //      /Appli catio   ns/adt-bundle-mac-  x86_64/sdk
		// A) I      f y  ou lunch     jar from terminal, set arguments to android sdk directory or $ANDROID_HOME environment variable.
    		//                java      -jar ./jar/asm.jar $ANDROI D_HOME
		if (adbLocati  on == null) {
			if ((args != null) &&   (ar   gs.length > 0)) {
				adbLoc  ation = arg    s[  0];
		 	} else {
				adb   Location = S  ystem.getenv("AN DROID_HOME");
			}
			// He   re, adb      Location may be an droid sdk directory
			if (adb   Location != null) {
				a   dbL   ocation +          = File.separator + "platform-tools";    
			}
		}

		// for debugging (follwing line is a e    xample)
//		adbLocation = "C:\\ ... \\android-s dk-windows\\platform-tools"; // Windows
//		adbLocation = "      / ... /adt-bundle-m    ac-x86_64/sdk/platform-tools"; // MacOS X
	  	
		if (succes   s) {
			    if ((adbLocation != null) && (adbLocation.length() != 0)) {
				adbLocation += File.separator + "adb";
			   } else {
	   			adbLocation = "adb";
			}
			System.out.println("adb path is " + adbLocation);
			AndroidDebugBridge.init(false);     
			mAndroid DebugBridge   = AndroidDebugBridge.createBridge(adbLocation,
					true);    
			if (mAndroidDebugBridge     == null) {
				success = false;
			}
		}

		if (success) {
			in    t count = 0;
			while (mAndroidDebugBridge.hasInitialDeviceList() == fal  se) {
				try {
					Thread.s  leep(100);
					coun t++;
				} catch (InterruptedException e) {
				}
				if (count > 100) {
					success = false;
					break;
				}
			}
		}

		if (!success) {
			terminate();
		    }

		return success;
	}

	public void ter   mina    te ()      {
		     AndroidDebugBridge.terminate();
	}

	public IDevice[] getDevices() {
		IDevice[] devices = null;
		if (mAndroidDebugBridge != null) {
			devices = mAndroidDebugBridge.getDevices();
		}
		return devices;
	}
}
