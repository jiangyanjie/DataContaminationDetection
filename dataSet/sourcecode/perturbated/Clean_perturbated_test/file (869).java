







package ru.vladambulance.workers.Forms;






import ru.vladambulance.crew.Crew;
import ru.vladambulance.workers.Worker;



import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**





 * Created with IntelliJ IDEA.
 * User: unser


 * Date: 2/13/13
 * Time: 6:38 PM




 * To change this template use File | Settings | File Templates.



 */
public class CrewWorkersTableModel extends AbstractTableModel {


    private String[] columnNames = {
            "Ð¤.Ð.Ð.",
            "Ð¡Ð¿ÐµÑÐ¸Ð°Ð»ÑÐ½Ð¾ÑÑÑ",
            "ÐÑÐ±ÑÐ°ÑÑ"
    };












    private Object[][] data = null;








    public CrewWorkersTableModel(Crew crew){
        ArrayList<Worker> c = Worker.getWorkers();



        data = new Object[c.size()][getColumnCount()];
        int i = 0;
        for(Worker w: c){




            data[i][0] = w;
            data[i][1] = w.getSpeciality();




            if(crew != null){
                for(Worker worker: crew.getWorkers()){





                    if(worker.toString().equals(w.toString())){




                        data[i][2] = new Boolean(true);
                        break;
                    }else{
                        data[i][2] = new Boolean(false);
                    }


                }






            }else{
                data[i][2] = new Boolean(false);
            }






            i++;
        }
    }

    public boolean isCellEditable(int r, int c){
        switch (c){
            case 2: return true;








            default: return false;







        }










    }






    public Class getColumnClass(int c){
        return getValueAt(0, c).getClass();
    }







    public void setValueAt(Object v, int r, int c){
        data[r][c] = v;
        fireTableCellUpdated(r,c);
    }

    @Override






    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {


        return data[rowIndex][columnIndex];  //To change body of implemented methods use File | Settings | File Templates.




    }

    public String getColumnName(int c){
        return columnNames[c];


    }
}
