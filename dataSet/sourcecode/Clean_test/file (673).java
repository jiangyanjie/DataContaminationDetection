package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import housedata.HPAdata;
import housedata.Housedata;
import housedata.ReadFile;
import mapdata.ConnectPoint;

public class CostAndRangeRanking {
	private List < CostAndRangeRankingList > CARRList = new ArrayList <> ();
	private HPAdata HPA2;
	private ReadFile RF;
	private int HouseNumber;
	private ConnectPoint CP;

	//�Q�b�^�[�E�Z�b�^�[
	public List<CostAndRangeRankingList> getCARRList() {
		return CARRList;
	}
	public void setCARRList(CostAndRangeRankingList costAndRangeRankingList) {
		CARRList.add (costAndRangeRankingList );
	}
	public HPAdata getHPA2 () {
		return HPA2;
	}
	public void setHPA2 ( HPAdata HPAAA ) {
		HPA2 = HPAAA;
	}
	public ReadFile getRF () {
		return RF;
	}

	public CostAndRangeRanking () {
		//MDD = new MinusDur();
		RF = new ReadFile();
		RF.CreatefromFile( ".\\recycle\\Houselist.txt" ); //�Ƌ�X�g�̍쐬
		HouseNumber = RF.getHouseList ().size (); //�Ƃ̐�
		CP = new ConnectPoint(); //�n�}���f���쐬
		CP.ConnectfromFile(".\\recycle\\Maptokyo.txt"); //�n�}���f���쐬
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void CARRCreate ( int TermCount ) {		
		HouseSearchNoneandDouble ( HouseNumber, RF, CP ); //�����Ƀ_�u��ƂȂ��̉Ƃ̌��������{
		CostAndRangeRankingList LowScore; //�n�C�X�R�A�ȉƂ�p�ӂ��Ă���
		List < CostAndRangeRankingList > Narabikae = getCARRList();
		Collections.sort( Narabikae, new RankingComparator() ); //�X�R�A�̒Ⴂ�҂���ɂ���i�܂�n�C�X�R�A
		for ( int i = 0; i < getCARRList().size(); i++ ) {
			LowScore = getCARRList().get(i);
			if ( LowScore.getHPA() != null ) {
				new Exchange ( LowScore.getHouseC1(), LowScore.getHouseC2(), LowScore.getHPA(), TermCount, LowScore ); //�n�C�X�R�A�Ȃ��̂Ō��������
			}
		}
		
		
		HouseSearch ( HouseNumber, RF, CP ); //���ɒʏ퓯�u�Ŏ��{
		Narabikae = getCARRList();
		Collections.sort( Narabikae, new RankingComparator() ); //�X�R�A�̒Ⴂ�҂���ɂ���i�܂�n�C�X�R�A
		for ( int i = 0; i < getCARRList().size(); i++ ) {
			LowScore = getCARRList().get(i);			
			if ( LowScore.getHPA() != null ) {
				new Exchange ( LowScore.getHouseC1(), LowScore.getHouseC2(), LowScore.getHPA(), TermCount, LowScore ); //�n�C�X�R�A�Ȃ��̂Ō��������
			}
		}
		MinusDur MDD = new MinusDur();
		MDD.Minus(RF);
	}
	private void HouseSearchNoneandDouble ( int HouseNumber, ReadFile RF, ConnectPoint CP  ) { //�i�C�l�Ƃ��Ԃ��Ă�l�ǂ����Ƃ��܂����
		for ( int i = 0; i < HouseNumber; i++ ) {
			Housedata A1 = null;
			Housedata A2 = null;
			if ( RF.getHouseList ().get ( i ).FurnitureList.size() == 0 ) A1 = RF.getHouseList ().get ( i ); //�����̎��
			for ( int j = 0; j < HouseNumber; j++ ) {
				if ( RF.getHouseList ().get ( i ).FurnitureList.size() >= 2 ) A2 = RF.getHouseList ().get ( j ); //�����
			}
			if ( A1 != null && A2 != null  ) {
				int Range = MARange ( A1, A2, CP );
				int Cost = FurnitureCost ( A1, A2 );
				new CostAndRangeRankingList ( Cost, Range, A1, A2, this, getHPA2() );
			}
		}
	}
	private void HouseSearch ( int HouseNumber, ReadFile RF, ConnectPoint CP ) { //�Ƃ̑������胁�\�b�h
		for ( int i = 0; i< HouseNumber; i++ ) { //�������i�̌���
			for ( int j = 0; j < HouseNumber; j++ ) {
				Housedata A1 = RF.getHouseList ().get ( i ); //�����̎��
				Housedata A2 = RF.getHouseList ().get ( j ); //�����
				if ( A1 != A2 && A2 != A1 ) { //i��j���Ⴄ�Ƃ��������������
					int Range = MARange ( A1, A2, CP );
					int Cost = FurnitureCost ( A1, A2 );
					new CostAndRangeRankingList ( Cost, Range, A1, A2, this, getHPA2() );
				}
			}
		}
	}
	
 	private int MARange ( Housedata B1, Housedata B2, ConnectPoint CP ) { //�ŒZ������Ԃ��� ���邢�݂����̃Q�b�^�[
		int PointNum = CP.getPitList().size(); //�n�}���f���̍s��
		
		int[][] RouteArray = new int [PointNum][PointNum]; //�n�}�s��̏�����
		for ( int i=0; i< PointNum; i++ ) {
			for ( int j =0; j< PointNum; j++) {
				RouteArray[i][j] = 0;
			}
		}
		
		String Name1 = B1.getName(); //�Ƃ̖��O���擾
		String Name2 = B2.getName();
		
		for ( int i = 0; i < CP.getPitList().size(); i++ ) { //�Ƃ̖��O���烋�[�g�𒲂ׂ�
			if ( Name1.equals ( CP.getPitList().get(i).getName() ) ) {
				for ( int j = 0; j < CP.getPitList().size(); j++ ) {
					if ( Name2.equals ( CP.getPitList().get(j).getName() ) ) {
						MinimumAccess MA = new MinimumAccess ( RouteArray, PointNum, i, j, CP ); //�s��ƂȂ��������āA�����ƃ��[�g��Ԃ�
						return MA.getLength();
					}
				}
			}
		}
		return 0;
	}
	
	private int FurnitureCost ( Housedata A1, Housedata A2 ) { //�Ƌ�̃R�X�g��Ԃ���
		ExchangeHPASearch EFS = new ExchangeHPASearch ( A1, A2 );
		setHPA2 ( EFS.getHPA() );
		return EFS.getCost();
	}
}


class CostAndRangeRankingList {
	private int Cost;
	private int Range;
	private Housedata C1;
	private Housedata C2;
	private int Score;
	private HPAdata HPA1;
	
	public int getCost() {
		return Cost;
	}
	public void setCost(int cost) {
		Cost = cost;
	}
	public void setScore(int score) {
		Score = score;
	}
	public int getRange() {
		return Range;
	}
	public int getScore() {
		return Score;
	}
	public HPAdata getHPA () {
		return HPA1;
	}
	public void setRange(int range) {
		Range = range;
	}
	public Housedata getHouseC1() {
		return C1;
	}
	public Housedata getHouseC2() {
		return C2;
	}
	public void setA1(Housedata a1) {
		C1 = a1;
	}
	public void setA2(Housedata a2) {
		C2 = a2;
		}
	public void setHPA ( HPAdata HPAA ) {
		HPA1 = HPAA;
	}
	
	public CostAndRangeRankingList ( int Co, int Ra, Housedata a1, Housedata a2, CostAndRangeRanking CARR, HPAdata HPA3 ) {
		setCost ( Co );
		setRange ( Ra );
		setA1 ( a1 );
		setA2 ( a2 );
		setScore ( Co * 1 );
		setHPA( HPA3 );
		CARR.setCARRList( this );
	}
}

@SuppressWarnings("rawtypes")
class RankingComparator implements java.util.Comparator {
	public int compare ( Object s, Object t ) {
		return ( ( CostAndRangeRankingList ) s ).getScore() - ( ( CostAndRangeRankingList ) t ).getScore();
	}
}
