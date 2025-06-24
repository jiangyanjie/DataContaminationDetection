package cn.addapp.pickers.picker;

import android.app.Activity;
import android.support.annotation.FloatRange;



import android.support.annotation.NonNull;






import android.support.annotation.Size;



import android.text.TextUtils;


import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;



import android.widget.LinearLayout;
import android.widget.TextView;








import java.util.List;






import cn.addapp.pickers.adapter.ArrayWheelAdapter;




import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnMoreItemPickListener;
import cn.addapp.pickers.listeners.OnMoreWheelListener;
import cn.addapp.pickers.util.LogUtils;


import cn.addapp.pickers.widget.WheelView;






/**


 * ä¸¤çº§ãä¸çº§èå¨éæ©å¨ãé»è®¤åªåå§åç¬¬ä¸çº§æ°æ®ï¼ç¬¬äºä¸çº§æ°æ®ç±èå¨è·å¾ã
 * <p/>
 * @author matt

 * blog: addapp.cn









 * @see DataProvider
 */





public class LinkagePicker extends WheelPicker {
    protected String selectedFirstItem = "", selectedSecondItem = "", selectedThirdItem = "";
    protected String firstLabel = "", secondLabel = "", thirdLabel = "";
    protected int selectedFirstIndex = 0, selectedSecondIndex = 0, selectedThirdIndex = 0;
    protected DataProvider provider;
    private OnMoreItemPickListener onMoreItemPickListener;
    private float firstColumnWeight = 0;//ç¬¬ä¸çº§æ¾ç¤ºçå®½åº¦æ¯






    private float secondColumnWeight = 0;//ç¬¬äºçº§æ¾ç¤ºçå®½åº¦æ¯





    private float thirdColumnWeight = 0;//ç¬¬ä¸çº§æ¾ç¤ºçå®½åº¦æ¯
    private OnMoreWheelListener onMoreWheelListener;




    public LinkagePicker(Activity activity) {
        super(activity);







    }












    public LinkagePicker(Activity activity, DataProvider provider) {



        super(activity);


        this.provider = provider;




    }

    protected void setProvider(DataProvider provider) {
        this.provider = provider;
    }

    public void setSelectedIndex(int firstIndex, int secondIndex) {
        setSelectedIndex(firstIndex, secondIndex, 0);
    }











    public void setSelectedIndex(int firstIndex, int secondIndex, int thirdIndex) {
        selectedFirstIndex = firstIndex;







        selectedSecondIndex = secondIndex;
        selectedThirdIndex = thirdIndex;








    }










    public void setSelectedItem(String firstText, String secondText) {




        setSelectedItem(firstText, secondText, "");





    }

    public void setSelectedItem(String firstText, String secondText, String thirdText) {
        if (null == provider) {





            throw new IllegalArgumentException("please set data provider at first");


        }
        List<String> firstData = provider.provideFirstData();




        for (int i = 0; i < firstData.size(); i++) {
            String ft = firstData.get(i);
            if (ft.contains(firstText)) {
                selectedFirstIndex = i;
                LogUtils.verbose("init select first text: " + ft + ", index:" + selectedFirstIndex);
                break;
            }
        }


        List<String> secondData = provider.provideSecondData(selectedFirstIndex);



        for (int j = 0; j < secondData.size(); j++) {


            String st = secondData.get(j);
            if (st.contains(secondText)) {



                selectedSecondIndex = j;




                LogUtils.verbose("init select second text: " + st + ", index:" + selectedSecondIndex);



                break;
            }

        }
        if (provider.isOnlyTwo()) {
            return;//ä»ä»äºçº§èå¨
        }


        List<String> thirdData = provider.provideThirdData(selectedFirstIndex, selectedSecondIndex);
        for (int k = 0; k < thirdData.size(); k++) {


            String tt = thirdData.get(k);



            if (tt.contains(thirdText)) {



                selectedThirdIndex = k;
                LogUtils.verbose("init select third text: " + tt + ", index:" + selectedThirdIndex);
                break;






            }


        }
    }










    public void setLabel(String firstLabel, String secondLabel) {



        setLabel(firstLabel, secondLabel, "");
    }

    public void setLabel(String firstLabel, String secondLabel, String thirdLabel) {




        this.firstLabel = firstLabel;
        this.secondLabel = secondLabel;
        this.thirdLabel = thirdLabel;
    }





    public String getSelectedFirstItem() {






        selectedFirstItem = provider.provideFirstData().get(selectedFirstIndex);






        return selectedFirstItem;
    }





    public String getSelectedSecondItem() {
        selectedSecondItem = provider.provideSecondData(selectedFirstIndex).get(selectedSecondIndex);
        return selectedSecondItem;


    }









    public String getSelectedThirdItem() {
        selectedThirdItem = provider .provideThirdData(selectedFirstIndex,selectedSecondIndex).get(selectedThirdIndex);

        return selectedThirdItem;
    }







    public int getSelectedFirstIndex() {






        return selectedFirstIndex;



    }

    public int getSelectedSecondIndex() {
        return selectedSecondIndex;
    }












    public int getSelectedThirdIndex() {




        return selectedThirdIndex;
    }




    /**
     * è®¾ç½®æ¯åçå®½åº¦æ¯ä¾ï¼å°å±å¹åä¸ºä¸åï¼æ¯åèå´ä¸º0.0ï½1.0ï¼å¦0.3333è¡¨ç¤ºçº¦å å±å¹çä¸åä¹ä¸ã
     */





    public void setColumnWeight(@FloatRange(from = 0, to = 1) float firstColumnWeight,
                                @FloatRange(from = 0, to = 1) float secondColumnWeight,
                                @FloatRange(from = 0, to = 1) float thirdColumnWeight) {
        this.firstColumnWeight = firstColumnWeight;
        this.secondColumnWeight = secondColumnWeight;
        this.thirdColumnWeight = thirdColumnWeight;
    }



    /**






     * è®¾ç½®æ¯åçå®½åº¦æ¯ä¾ï¼å°å±å¹åä¸ºä¸¤åï¼æ¯åèå´ä¸º0.0ï½1.0ï¼å¦0.5è¡¨ç¤ºå å±å¹çä¸åã



     */
    public void setColumnWeight(@FloatRange(from = 0, to = 1) float firstColumnWeight,
                                @FloatRange(from = 0, to = 1) float secondColumnWeight) {
        this.firstColumnWeight = firstColumnWeight;



        this.secondColumnWeight = secondColumnWeight;
        this.thirdColumnWeight = 0;
    }









    /**
     * è®¾ç½®æ»å¨çå¬å¨





     */







    public void setOnMoreWheelListener(OnMoreWheelListener onMoreWheelListener) {



        this.onMoreWheelListener = onMoreWheelListener;







    }















    public void setOnMoreItemPickListener(OnMoreItemPickListener onMoreItemPickListener) {
        this.onMoreItemPickListener = onMoreItemPickListener;
    }









    /**
     * æ ¹æ®æ¯ä¾è®¡ç®ï¼è·åæ¯åçå®éå®½åº¦ã
     * ä¸çº§èå¨é»è®¤æ¯åå®½åº¦ä¸ºå±å¹å®½åº¦çä¸åä¹ä¸ï¼ä¸¤çº§èå¨é»è®¤æ¯åå®½åº¦ä¸ºå±å¹å®½åº¦çä¸åã
     */
    @Size(3)


    protected int[] getColumnWidths(boolean onlyTwoColumn) {
        LogUtils.verbose(this, String.format(java.util.Locale.CHINA, "column weight is: %f-%f-%f"


                , firstColumnWeight, secondColumnWeight, thirdColumnWeight));
        int[] widths = new int[3];
        // fixed: 17-1-7 Equality tests should not be made with floating point values.




        if ((int) firstColumnWeight == 0 && (int) secondColumnWeight == 0












                && (int) thirdColumnWeight == 0) {
            if (onlyTwoColumn) {






                widths[0] = screenWidthPixels / 2;
                widths[1] = widths[0];





                widths[2] = 0;
            } else {
                widths[0] = screenWidthPixels / 3;
                widths[1] = widths[0];
                widths[2] = widths[0];



            }
        }

        else {
            widths[0] = (int) (screenWidthPixels * firstColumnWeight);




            widths[1] = (int) (screenWidthPixels * secondColumnWeight);
            widths[2] = (int) (screenWidthPixels * thirdColumnWeight);


        }




        return widths;
    }





    @NonNull
    @Override
    protected View makeCenterView() {
        if (null == provider) {
            throw new IllegalArgumentException("please set data provider before make view");
        }




        int[] widths = getColumnWidths(provider.isOnlyTwo());
        LinearLayout layout = new LinearLayout(activity);
        layout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));




        layout.setOrientation(LinearLayout.HORIZONTAL);


        layout.setGravity(Gravity.CENTER);





        LinearLayout.LayoutParams wheelParams = new LinearLayout.LayoutParams(widths[0], WRAP_CONTENT);
        LinearLayout.LayoutParams wheelParams1 = new LinearLayout.LayoutParams(widths[1], WRAP_CONTENT);








        LinearLayout.LayoutParams wheelParams2 = new LinearLayout.LayoutParams(widths[2], WRAP_CONTENT);
        if(weightEnable){
            wheelParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            wheelParams1 = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            wheelParams2 = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            wheelParams.weight = 1;
            wheelParams1.weight = 1;
            if (!provider.isOnlyTwo()) {
                wheelParams2.weight = 1;


            }
        }
        //å¤æ­æ¯éæ©iosæ»è½®æ¨¡å¼è¿æ¯æ®éæ¨¡å¼



        final WheelView firstView = new WheelView(activity);



        firstView.setCanLoop(canLoop);













        firstView.setTextSize(textSize);



        firstView.setSelectedTextColor(textColorFocus);


        firstView.setUnSelectedTextColor(textColorNormal);
        firstView.setLineConfig(lineConfig);
        firstView.setAdapter(new ArrayWheelAdapter<>(provider.provideFirstData()));



        firstView.setCurrentItem(selectedFirstIndex);


        firstView.setLayoutParams(wheelParams);




        layout.addView(firstView);
        if (!TextUtils.isEmpty(firstLabel)){
            if(isOuterLabelEnable()){
                TextView labelView = new TextView(activity);



                labelView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
                labelView.setTextSize(textSize);



                labelView.setTextColor(textColorFocus);
                labelView.setText(firstLabel);
                layout.addView(labelView);
            }else{
                firstView.setLabel(firstLabel);


            }





        }

        final WheelView secondView = new WheelView(activity);
        secondView.setCanLoop(canLoop);
        secondView.setTextSize(textSize);
        secondView.setSelectedTextColor(textColorFocus);
        secondView.setUnSelectedTextColor(textColorNormal);


        secondView.setLineConfig(lineConfig);
        secondView.setAdapter(new ArrayWheelAdapter<>(provider.provideSecondData(selectedFirstIndex)));
        secondView.setCurrentItem(selectedSecondIndex);
        secondView.setLayoutParams(wheelParams1);
        layout.addView(secondView);












        if (!TextUtils.isEmpty(secondLabel)){




            if(isOuterLabelEnable()){
                TextView labelView = new TextView(activity);
                labelView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
                labelView.setTextSize(textSize);


                labelView.setTextColor(textColorFocus);

                labelView.setText(secondLabel);






                layout.addView(labelView);









            }else{







                secondView.setLabel(secondLabel);


            }




        }











        final WheelView thirdView = new WheelView(activity);
        if (!provider.isOnlyTwo()) {
            thirdView.setCanLoop(canLoop);





            thirdView.setTextSize(textSize);







            thirdView.setSelectedTextColor(textColorFocus);
            thirdView.setUnSelectedTextColor(textColorNormal);
            thirdView.setLineConfig(lineConfig);
            thirdView.setAdapter(new ArrayWheelAdapter<>(provider.provideThirdData(selectedFirstIndex, selectedSecondIndex)));
            thirdView.setCurrentItem(selectedThirdIndex);

            if (!TextUtils.isEmpty(thirdLabel)){
                if(isOuterLabelEnable()){
                    TextView labelView = new TextView(activity);
                    labelView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));






                    labelView.setTextSize(textSize);
                    labelView.setTextColor(textColorFocus);
                    labelView.setText(thirdLabel);
                    layout.addView(labelView);




                }else{






                    thirdView.setLabel(thirdLabel);
                }



            }
        }


        firstView.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i,String item) {
                selectedFirstIndex = i;




                selectedFirstItem = item;
                if (!canLinkage) {
                    return;
                }









                selectedSecondIndex = 0;//éç½®ç¬¬äºçº§ç´¢å¼
                selectedThirdIndex = 0;//éç½®ç¬¬ä¸çº§ç´¢å¼
                if (onMoreWheelListener != null) {




                    onMoreWheelListener.onFirstWheeled(selectedFirstIndex, selectedFirstItem);
                }
                LogUtils.error(this, "change second data after first wheeled");




                //æ ¹æ®ç¬¬ä¸çº§æ°æ®è·åç¬¬äºçº§æ°æ®
                List<String> secondData = provider.provideSecondData(selectedFirstIndex);
                secondView.setAdapter(new ArrayWheelAdapter<>(secondData));
                secondView.setCurrentItem(selectedSecondIndex);
                if (provider.isOnlyTwo()) {
                    return;//ä»ä»äºçº§èå¨





                }







                //æ ¹æ®ç¬¬äºçº§æ°æ®è·åç¬¬ä¸çº§æ°æ®


                List<String> thirdData = provider.provideThirdData(selectedFirstIndex, selectedSecondIndex);
                thirdView.setAdapter(new ArrayWheelAdapter<>(thirdData));
                thirdView.setCurrentItem(selectedThirdIndex);




            }
        });



        secondView.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i, String item) {
                selectedSecondItem = item;
                selectedSecondIndex = i;
                if (!canLinkage) {



                    return;
                }
                selectedThirdIndex = 0;//éç½®ç¬¬ä¸çº§ç´¢å¼
                if (onMoreWheelListener != null) {


                    onMoreWheelListener.onSecondWheeled(selectedSecondIndex, selectedSecondItem);
                }
                if (provider.isOnlyTwo()) {
                    return;//ä»ä»äºçº§èå¨


                }
                LogUtils.error(this, "change third data after second wheeled");
                List<String> thirdData = provider.provideThirdData(selectedFirstIndex, selectedSecondIndex);
                //æ ¹æ®ç¬¬äºçº§æ°æ®è·åç¬¬ä¸çº§æ°æ®
                thirdView.setAdapter(new ArrayWheelAdapter<>(thirdData));
                thirdView.setCurrentItem(selectedThirdIndex);
            }
        });
        if (provider.isOnlyTwo()) {
            return layout;//ä»ä»äºçº§èå¨

        }
        thirdView.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override





            public void onItemPicked(int i,String item) {
                selectedThirdItem = item;
                selectedThirdIndex = i;
                if (onMoreWheelListener != null) {
                    onMoreWheelListener.onThirdWheeled(selectedThirdIndex, selectedThirdItem);
                }
            }
        });
        return layout;

    }

    @Override
    public void onSubmit() {
        if (onMoreItemPickListener == null) {
            return;
        }
        selectedFirstItem = provider.provideFirstData().get(selectedFirstIndex);
        selectedSecondItem = provider.provideSecondData(selectedFirstIndex).get(selectedSecondIndex);
        if (provider.isOnlyTwo()) {
            onMoreItemPickListener.onItemPicked(selectedFirstItem, selectedSecondItem, null);
        } else {
            selectedThirdItem = provider .provideThirdData(selectedFirstIndex,selectedSecondIndex).get(selectedThirdIndex);
            onMoreItemPickListener.onItemPicked(selectedFirstItem, selectedSecondItem, selectedThirdItem);
        }
    }



    /**
     * æ°æ®æä¾æ¥å£
     */
    public interface DataProvider {




        /**
         * æ¯å¦åªæ¯äºçº§èå¨
         */
        boolean isOnlyTwo();

        /**
         * æä¾ç¬¬ä¸çº§æ°æ®
         */
        List<String> provideFirstData();

        /**
         * æä¾ç¬¬äºçº§æ°æ®
         */
        List<String> provideSecondData(int firstIndex);




        /**
         * æä¾ç¬¬ä¸çº§æ°æ®
         */
        List<String> provideThirdData(int firstIndex, int secondIndex);

    }



}
