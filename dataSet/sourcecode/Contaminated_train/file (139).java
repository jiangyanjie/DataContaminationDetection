package com.example.mobilefactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import com.example.mobileservice.Manufacturer;
import com.example.mobileservice.Mobile;
import com.example.mobileservice.Part;
import com.example.mobileservice.PartType;

public abstract class AbstractMobileFactory implements MobileFactory {

	private static final List<MobileFactory> FACTORIES = new ArrayList<>();
	public static MobileFactory INSTANCE;

	protected static final void registerFactory(MobileFactory factory) {
		FACTORIES.add(factory);
	}

	static {
		ServiceLoader<MobileFactory> factoryImplementations = ServiceLoader.load(MobileFactory.class);
		for (MobileFactory mobileFactory : factoryImplementations) {
			registerFactory(mobileFactory);
		}
	}

	public static List<MobileFactory> getFactories() {
		return new ArrayList<>(FACTORIES);
	}

	protected abstract String getManufacturerName();

	protected abstract Manufacturer getManufacturer();

	protected abstract String[] getModels();

	@Override
	public Mobile createPhone() {
		Mobile mobile = new Mobile();
		mobile.manufacturer = getManufacturer();
		mobile.model = getModels()[RANDOM.nextInt(getModels().length)];
		mobile.display = new Part(PartType.DISPLAY, RANDOM.nextBoolean(), getManufacturerName() + " Display");
		mobile.microphone = new Part(PartType.MICROPHONE, RANDOM.nextBoolean(), getManufacturerName() + " Microphone");
		mobile.motherBoard = new Part(PartType.MOTHERBOARD, RANDOM.nextBoolean(), getManufacturerName() + " " + mobile.model + " Motherboard");
		mobile.powerSwitch = new Part(PartType.POWER_SWITCH, RANDOM.nextBoolean(), getManufacturerName() + " Power Switch");
		mobile.speaker = new Part(PartType.SPEAKER, RANDOM.nextBoolean(), getManufacturerName() + " Speaker");
		mobile.volumeButtons = new Part(PartType.VOLUME_BUTTONS, RANDOM.nextBoolean(), getManufacturerName() + " Volume Buttons");
		return mobile;
	}

}
