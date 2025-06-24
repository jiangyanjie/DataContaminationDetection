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
package com.emitrom.pilot.device.client.accelerometer;

import com.emitrom.pilot.device.client.core.Module;
import com.emitrom.pilot.device.client.core.ModuleFactory;
import com.emitrom.pilot.device.client.core.handlers.accelerometer.AccelerometerHandler;

/**
 * The accelerometer is a motion sensor that detects the change (delta) in
 * movement relative to the current device orientation. The accelerometer can
 * detect 3D movement along the x, y, and z axis.<br/>
 * 
 * The acceleration is returned using the AccelerometerSuccessHandler callback <br/>
 * Supported platforms: <br/>
 * <ul>
 * <li>Android</li>
 * <li>BlackBerry WebWorks (OS 5.0 and higher)</li>
 * <li>iPhone</li>
 * <li>Windows Phone 7 and 8</li>
 * <li>Bada 1.2 & 2.x</li>
 * <li>Tizen</li>
 * </ul>
 * <h2>iPhone Quirks</h2>
 * <ul>
 * <li>iPhone doesn't have the concept of getting the current acceleration at
 * any given point.</li>
 * <li>You must watch the acceleration and capture the data at given time
 * intervals.</li>
 * <li>Thus, the getCurrentAcceleration function will give you the last value
 * reported from a phoneGap watchAccelerometer call.</li>
 * </ul>
 * 
 * @see <a href=http://docs.phonegap.com/en/2.7.0/cordova_accelerometer_accelerometer.md.html#Accelerometer>
 * http://docs.phonegap.com/en/2.7.0/cordova_accelerometer_accelerometer.md.html#Accelerometer</a>
 */
public class Accelerometer extends Module {

    private static Accelerometer instance;

    public static Accelerometer get() {
        if (instance == null) {
            instance = new Accelerometer();
        }
        return instance;
    }

    /**
     * Gets the current acceleration along the x, y, and z axis.
     * 
     * @param callBack
     */
    public native void getCurrentAcceleration(AccelerometerHandler callBack)/*-{
		var peer = this.@com.emitrom.pilot.util.client.core.JsObject::getJsObj()();
		peer
				.getCurrentAcceleration(
						$entry(function(acceleration) {
							var object = @com.emitrom.pilot.device.client.accelerometer.Acceleration::new(Lcom/google/gwt/core/client/JavaScriptObject;)(acceleration);
							callBack.@com.emitrom.pilot.device.client.core.handlers.accelerometer.AccelerometerHandler::onSuccess(Lcom/emitrom/pilot/device/client/accelerometer/Acceleration;)(object);
						}),
						$entry(function() {
							callBack.@com.emitrom.pilot.device.client.core.handlers.accelerometer.AccelerometerHandler::onError()();
						}));
    }-*/;

    /**
     * At a regular interval, get the acceleration along the x, y, and z axis.
     * 
     * @param callBack
     */
    public native String watchAcceleration(AccelerometerHandler callBack)/*-{
		var peer = this.@com.emitrom.pilot.util.client.core.JsObject::getJsObj()();
		return peer
				.watchAcceleration(
						$entry(function(acceleration) {
							var object = @com.emitrom.pilot.device.client.accelerometer.Acceleration::new(Lcom/google/gwt/core/client/JavaScriptObject;)(acceleration);
							callBack.@com.emitrom.pilot.device.client.core.handlers.accelerometer.AccelerometerHandler::onSuccess(Lcom/emitrom/pilot/device/client/accelerometer/Acceleration;)(object);
						}),
						$entry(function() {
							callBack.@com.emitrom.pilot.device.client.core.handlers.accelerometer.AccelerometerHandler::onError()();
						}));
    }-*/;

    /**
     * At a regular interval, get the acceleration along the x, y, and z axis.
     * 
     * @param callBack
     * @param options
     */
    public native String watchAcceleration(AccelerometerHandler callBack, AccelerometerOptions options)/*-{
		var peer = this.@com.emitrom.pilot.util.client.core.JsObject::getJsObj()();
		return peer
				.watchAcceleration(
						$entry(function(acceleration) {
							var object = @com.emitrom.pilot.device.client.accelerometer.Acceleration::new(Lcom/google/gwt/core/client/JavaScriptObject;)(acceleration);
							callBack.@com.emitrom.pilot.device.client.core.handlers.accelerometer.AccelerometerHandler::onSuccess(Lcom/emitrom/pilot/device/client/accelerometer/Acceleration;)(object);
						}),
						$entry(function() {
							callBack.@com.emitrom.pilot.device.client.core.handlers.accelerometer.AccelerometerHandler::onError()();
						}),
						options.@com.emitrom.pilot.util.client.core.JsObject::getJsObj()());
    }-*/;

    /**
     * Stop watching the Acceleration referenced by the watch ID parameter.
     * 
     * @param watchId
     *            , The ID returned by Accelerometer.watchAcceleration.
     */
    public native void clearWatch(String watchId)/*-{
		var peer = this.@com.emitrom.pilot.util.client.core.JsObject::getJsObj()();
		peer.clearWatch(watchId);
    }-*/;

    private Accelerometer() {
        createPeer();
    }

    @Override
    public void createPeer() {
        jsObj = ModuleFactory.createNativeAcceleromater();
    }

}
