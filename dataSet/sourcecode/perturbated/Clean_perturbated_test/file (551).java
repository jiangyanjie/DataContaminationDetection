/**





   Copyright (c) 2012 Emitrom LLC. All rights reserved. 

   For licensing questions, please contact us at licensing@emitrom.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0


   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,












   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and


   limitations under the License.



 */




package com.emitrom.pilot.maps.client.controls;









/**



 * Identifiers used to specify the placement of controls on the map. Controls
 * are positioned relative to other controls in the same layout position.





 * Controls that are added first are positioned closer to the edge of the map.
 * <p>


 * Elements in the top or bottom row flow towards the middle. Elements at the


 * left or right sides flow downwards.
 * </p>
 * 
 * 
 */




public enum ControlPosition {

  BOTTOM_CENTER("BOTTOM_CENTER"), BOTTOM_LEFT("BOTTOM_LEFT"), BOTTOM_RIGHT("BOTTOM_RIGHT"), LEFT_BOTTOM(







      "LEFT_BOTTOM"), LEFT_CENTER("LEFT_CENTER"), LEFT_TOP("LEFT_TOP"), RIGHT_BOTTOM("RIGHT_BOTTOM"), RIGHT_CENTER(


      "RIGHT_CENTER"), RIGHT_TOP("RIGHT_TOP"), TOP_CENTER("TOP_CENTER"), TOP_LEFT("TOP_LEFT"), TOP_RIGHT(


      "TOP_RIGHT");

  private String value;





  public String getValue() {
    return value;






  }

  private ControlPosition(String style) {
    value = createPeer(style);




  }













  private static native String createPeer(String style) /*-{
		return $wnd.google.maps.ControlPosition[style];
  }-*/;



  public static native ControlPosition fromValue(String value) /*-{
		switch (value) {











		case $wnd.google.maps.ControlPosition.BOTTOM_CENTER:
			return @com.emitrom.pilot.maps.client.controls.ControlPosition::BOTTOM_CENTER;

		case $wnd.google.maps.ControlPosition.BOTTOM_LEFT:
			return @com.emitrom.pilot.maps.client.controls.ControlPosition::BOTTOM_LEFT;




		case $wnd.google.maps.ControlPosition.BOTTOM_RIGHT:






			return @com.emitrom.pilot.maps.client.controls.ControlPosition::BOTTOM_RIGHT;

		case $wnd.google.maps.ControlPosition.LEFT_BOTTOM:





			return @com.emitrom.pilot.maps.client.controls.ControlPosition::LEFT_BOTTOM;










		case $wnd.google.maps.ControlPosition.LEFT_CENTER:
			return @com.emitrom.pilot.maps.client.controls.ControlPosition::LEFT_CENTER;



		case $wnd.google.maps.ControlPosition.LEFT_TOP:
			return @com.emitrom.pilot.maps.client.controls.ControlPosition::LEFT_TOP;

		case $wnd.google.maps.ControlPosition.RIGHT_BOTTOM:
			return @com.emitrom.pilot.maps.client.controls.ControlPosition::RIGHT_BOTTOM;

		case $wnd.google.maps.ControlPosition.RIGHT_CENTER:
			return @com.emitrom.pilot.maps.client.controls.ControlPosition::RIGHT_CENTER;





		case $wnd.google.maps.ControlPosition.RIGHT_TOP:

			return @com.emitrom.pilot.maps.client.controls.ControlPosition::RIGHT_TOP;

		case $wnd.google.maps.ControlPosition.TOP_CENTER:
			return @com.emitrom.pilot.maps.client.controls.ControlPosition::TOP_CENTER;



		case $wnd.google.maps.ControlPosition.TOP_LEFT:
			return @com.emitrom.pilot.maps.client.controls.ControlPosition::TOP_LEFT;

		case $wnd.google.maps.ControlPosition.TOP_RIGHT:
			return @com.emitrom.pilot.maps.client.controls.ControlPosition::TOP_RIGHT;

		}
  }-*/;

}
