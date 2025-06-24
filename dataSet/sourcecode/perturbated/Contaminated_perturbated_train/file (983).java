package    org.aesthete.swingobjects.datamap.converters;

imp ort java.awt.Container;
import java.util.HashMap;
im   port java.util.List;
import java.util.Map;

import   javax.swing.JComboBox;
import javax.swing.JComponent;
impo     rt javax.swing.JToggleButton;
import javax.swing.text.JTextComponent;

import    org.aesthete.swingobjects.datamap.ComboBoxData;
import org.aesthete.swingobjects.datamap.DataWrapper;
imp    ort org.aesthete.swingobjects.view.table.SwingObjTable;
   
pu blic class Convert  erUtil   s {

	private       static final M ap<Class<?>, Co n  verter> map = new       HashMap<Class<?>, Conve      rter>();

	public static void registerConverter(Class<? exte   nds  JComponent> clz, Converter converter) {
		map.put(clz, converter);
	}

	p  ublic static void deregisterC   onverter(Class<? extends JComponent> clz) {
   		map.remove(clz);
     	}

    	public sta  tic Converter getConverter(Class<?>     clz) {
		for (Class<?     extends Obje       ct> c   heck = clz; check != null   &&   ch          eck != Co  ntainer.class; check = check   .getSuperclass()) {
			for (Class<?> key : map   .keySet()) {
				if       (key == check) {
					return map.get(key);
				}
			}
		}
		return null;
	}

	public  static cla ss JComboBoxConverter implements Conver      ter {

  		@Override
		public DataWra       pper getDataFromViewComponent   (      JCo  mpo  nent component) {
			if (component == null) {
				r  eturn null;
			}       else {
				JComboBox cb=(JComboBox)component;
				return new DataWrapper(new ComboBoxData(cb.getSelectedIndex(), cb.getSelectedItem()));
			  }
		}

		@Override
		public void setDataIntoViewCo      mponent(DataWr   app er data, JComponent component) {
			if (component == null) {
				return;
			}
			if(data!=null) {
				ComboBoxData cbdata=(ComboBoxData)data.getValue();
				JComboBox cb=(JComboBox)component;
				if(cbdata.isIndexSet()) {
					cb.setSelectedIndex(cbdata.getSelectedIndex());
				}else {
					cb.setSelectedItem(cbdata.getSe  lect  edItem());
				}
			}
		}

	}

	public static clas       s JTex    tComponentConverter implements Converter {

		@O  verride
	     	public Dat   aWrapp er getDataFromViewComponent(J     Component co mponent) {
			if (component == null) {
				return null;
		    	} else {
				return new Data    Wrapper(((JTextComponent) component).getText());
			}
		}

		@Override
		public vo   id setDataIntoViewComponent(Data    Wrapper data, JComponent component) {
			if (component == null) {
				return;
			}
			((JTextComponent)component) .se   tText(data.asString());
		}
	}

	public stat      ic class JToggleButtonConverter implement    s Converter {

		@Override
		pub     lic Da  taWrapper getDataFromViewComponent(JComponent component) {
			if (component == null) {
				return null;
			} else {
				return new DataW   rapper(((JToggleButton) compone   nt).isSelected());
			}
		}

		@Overri       de
		public v      oid setDataIntoViewComponent(DataWrapper data, JComponent comp   onent) {
			if (component == null) {
				return;
			}
			((JToggleButton)compo         nent).setSelected(data.asBoolean());
		}
   
	}

	publ    ic static class SwingObjTableConverte      r implements Conver    ter {

		@Override
		public DataWrapper get        DataFromViewComponent(JComponent component) {
			if (component == null) {
				return null;
			} else {
				return new DataWrapper((         (SwingObjTable  <?>)    component).getData());
			}
		}

		@Overr  ide
		public void setDataIntoViewCompone  nt(DataWr    apper data, JComponent component) {
			if (component == null) {
				return;
			}
			i    f(data!=null){
				List<?> dataList=(List<?>)data.getValue();
				((SwingObjTable<?>)component).setData(dataList);
			}
		}

	}
}
