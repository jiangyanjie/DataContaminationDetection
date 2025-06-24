package mapdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ConnectPoint extends ConnectPoint_Elements {
	private List < Point > PointList = new ArrayList <> (); //�|�C���g���̂��̂̃��X�g
	private List < Routedata > RouteList = new ArrayList <> (); //���̃��X�g

	//�Z�b�^�[��
	public void setPoint ( Point Pit ) { //�|�C���g���X�g�̃Z�b�^�[
		PointList.add ( Pit );
	}
	public void setRoute ( Routedata Rte ) { //�����X�g�̃Z�b�^�[
		RouteList.add ( Rte );
	}
	//�����܂ŃZ�b�^�[��
	
	//�Q�b�^�[��
	public List < Point > getPitList () { //PointList���̂��̂������Ă���Q�b�^�[
		List < Point > PitList = PointList; 
		return PitList;	
	}
	public List < Routedata > getRteList () { //RouteList���̂��̂������Ă���Q�b�^�[
		List < Routedata > RteList = RouteList; 
		return RteList;	
	}
	public Point getPoint ( int num ) { //�w��ԍ��̃|�C���g�̃Q�b�^�[
		Point Pit = PointList.get ( num );
		return Pit;
	}
	public Routedata getRoute ( int num ) { //�w��ԍ��̓��̃Q�b�^�[
		Routedata Rte = RouteList.get ( num );
		return Rte;
	}
	//�����܂ŃQ�b�^�[��
	
	private void Flug ( int num, int lineNum, String str ) { //�t���O�ɂ��G���[����
		switch ( num ) {
			case 1:
				System.out.print ( "Error: �����̃}�b�v�����L�q����Ă��܂��A�ǂꂩ��ɓ��ꂵ�Ă�������" );
				break;
			case 2:
				System.out.print ( "Error: �������O�̃|�C���g�����łɑ��݂��Ă��܂�" );
				break;
			case 3:
				System.out.print ( "Error: �����o�H�̓������łɑ��݂��Ă��܂�" );
				break;
			case 4:
				System.out.print ( "Error: ���݂��Ȃ��|�C���g���Q�Ƃ��悤�Ƃ��铹�ł��B�@���̖��O���C�����邩�A�Ԃ���m�F���Ă�������" );
				break;
			case 5:
				System.out.print ( "Error: �ǂݍ��݂Ɋ֌W�̂Ȃ��s���}������Ă��܂��B�@���̍s�͓ǂݔ�΂��܂��B�@�X�y���~�X�A�������`�F�b�N���Ă�������" );
				break;
			case 6:
				System.out.println ( "Error: ���̋L�q�����ɖ�肪����܂� �Ȃ�����|�C���g�͓���{�ɂ��A��ł�" );
				break;
			default:
				System.out.print ( "Error: ���̑��̃G���[���������Ă��܂��B�@�Y������s���m�F���邩�A�Ǘ��҂ɖ₢���킹�Ă�������" );
				break;
		}
		System.out.println ( "( �s�ԍ�" + lineNum + ": " + str + " )" ); //�G���[���b�Z�[�W���o������́A�K�����̃G���[�̂���s�����߂�
	}
	
	private Point findNamedPoint ( List < Point > Pit, String Name, String str, int CountLine ) { //�������O�̃|�C���g�������郁�\�b�h
		Point C = null; //�p�ӂ��Ă����Ȃ���Eclipse���G���[�f��
		for ( Iterator < Point > i = Pit.iterator (); i.hasNext (); ) { //PointList�̒��g�S���ɑ΂��Č�����������
			Point B = i.next ();
			if ( Name.equals ( B.getName () ) ) return C = B; //�����|�C���g����������C�֑���������Ԃ�
		}
		if ( C == null ) Flug ( 4, CountLine, str ); //null�̏ꍇ�̃G���[�����A�t���O���G���[�������\�b�h�֓�����
		return C;
	}
	
	private int PointDuplicationSearch ( String PitDupName ) { //�|�C���g���̏d������
		List < Point > SearchList = getPitList (); //�����̃|�C���g���X�g��ǂݍ���
		int findExistingName = -1; //�������O����t���O
		if ( SearchList.size () != 0 ) { //��ԍŏ��Ɍ��o�����|�C���g���̓f�t�H���g�Œǉ�
			for ( int i = 0; i < SearchList.size (); i++ ) { //���ׂẴ|�C���g�̖��O�Ɋւ��đ�������Ō�����������
				if ( SearchList.get ( i ).getName ().equals ( PitDupName ) ) {
					findExistingName = 1; //�����̖��O�������
					break;
				}
				else findExistingName = -1; //�Ȃ����-1��Ԃ�
			}
		}
		return findExistingName;
	}
	
	private int RouteDuplicationSearch( String RteDupNameIn, String RteDupNameOut ) { //�����̏d������
		List < Routedata > SearchList = getRteList (); //�����̃|�C���g���X�g��ǂݍ���
		int findExistingName = -1; //�������O����t���O
		if ( SearchList.size () != 0 ) { //��ԍŏ��Ɍ��o���������̓f�t�H���g�Œǉ�
			for ( int i = 0; i < SearchList.size (); i++ ) { //���ׂẴ|�C���g�̖��O�Ɋւ��đ�������Ō�����������
				if ( SearchList.get ( i ).getName ().equals ( RteDupNameIn + ", " + RteDupNameOut ) ) {
					findExistingName = 1; //�����̖��O�������
					break;
				}
				else findExistingName = -1; //�Ȃ����-1��Ԃ�
			}
		}
		return findExistingName;
	}
	
	//�e�ǂݍ��݊֐�
	private void reedingMapName ( String str, int CountLine) { //���C�t�T�C�N�����̓ǂݍ���
		String[] strAry = str.split ( " ", 2 );
		if ( getName () != null ) Flug ( 1, CountLine, str ); //���łɖ��O���͂����Ă���i�K��LifeCycleName�s����񂾂�G���[���o��
		else setName ( strAry[1] ); //�s����"MapName "�����O���A�k���̏ꍇ���O�֑��
	}
	
	private void reedingPointName ( String str, int CountLine ) { //�|�C���g���̓ǂݍ���
		String[] strAry = str.split( ", ", 5 );
		if (  PointDuplicationSearch ( strAry[1] ) == -1 )
			new Point ( this, strAry[1], Integer.parseInt( strAry[2] ), Integer.parseInt( strAry[3] ), Integer.parseInt( strAry[4] ) ); //�s����"Point "�����O���|�C���g���O���擾���A�|�C���g�����̖��O�ƍ��W�ō쐬
		else Flug ( 2, CountLine, str ); //�G���[����
	}
	
	private void reedingRouteName ( String str, int CountLine ) { //�����̓ǂݍ���
		String[] strAry = str.split( " ", 3 );
		String strRoute = strAry[1]; //�s����"Route "�����O
		String[] RouteName = strRoute.split ( ",", 0 ); //�z��RouteName��","�ŕ����������̂���
		if ( RouteName.length < 3 ) { //���͓�̃|�C���g�����ނ��΂Ȃ���
			Point fromPoint = findNamedPoint ( PointList, RouteName[0], str, CountLine ); //�����������đ��
			Point toPoint = findNamedPoint ( PointList, RouteName[1], str, CountLine ); //�����������đ��				
			if ( fromPoint != null & toPoint != null ){ //null�łȂ��ꍇ�ɓ������ null�悯
				if ( RouteDuplicationSearch( RouteName[0], RouteName[1] ) == -1 ) { //�d������
					Routedata A = new Routedata (this, fromPoint, toPoint, Integer.parseInt( strAry[2] ) ); //�����쐬
					fromPoint.setRoutedataOut ( A ); //RouteOut�֑��
					toPoint.setRoutedataIn ( A ); //RouteIn�֑��
				}
				else Flug (3, CountLine, str); //�d�����𔭌��̏ꍇ�̃G���[
			}
		}
		else Flug ( 6, CountLine, str ); //���̏����G���[�r��
	}
	//�����܂œǂݍ��݊֐�
	
	public void ConnectfromFile ( String DataFolder ) { //�����Ƀt�@�C���̈ʒu�𓊂���ƃt�@�C�����烉�C�t�T�C�N���𐶐�
		try {
			//�t�@�C�����[�_�[
			 File file = new File ( DataFolder );
			 BufferedReader br = new BufferedReader ( new FileReader ( file ) );
			//�����܂Ńt�@�C�����[�_�[
			 
			 String str = "0" ; //�ǂݎ��pString�֐� �������k���ɂ���Ǝ~�܂�i������O���j
			 int CountLine = 0; //�s�ԍ����Z�ϐ��i�G���[�����p�j
			
			//�ǂݍ��݃��\�b�h
			while ( str != null ) { //EOF��null�Ƃ��Č��o�A����܂ł͓ǂݍ��ݑ�����
				str = br.readLine (); CountLine++; //��s��ǂݍ��� �ǂݍ��ނ��тɎ���if���V�[�P���X�Ŕ���A�����ɍs�ԍ����Z
				if (str != null) { //�ǂݍ��ݍs��null�łȂ���΂��̃V�[�P���X�����s�A�s���Ŕ��ʂ���
					if ( str.startsWith ( "MapName" ) ) reedingMapName ( str, CountLine ); //���C�t�T�C�N�����̓ǂݍ���
					else if ( str.startsWith ( "Point" ) ) reedingPointName ( str, CountLine ); //�|�C���g�̓ǂݍ���
					else if ( str.startsWith ( "Route" ) ) reedingRouteName ( str, CountLine ); //���̓ǂݍ���
					else if ( str.startsWith ( "//" ) ); //�R�����g�������o����Ɖ������Ȃ�
					else Flug ( 5, CountLine, str ); //��̕��͂�ǂݍ��ނƃG���[��\�����Ė���
				}			
			}
			//�����܂œǂݍ��݃��\�b�h
			
			br.close(); //�t�@�C�������			
		} catch ( FileNotFoundException e ) { //��O����
			System.err.println ( e.getMessage () ); //�t�@�C���Ȃ��̗�O	
			System.exit ( 0 );
		} catch ( IOException e ) {
		    System.err.println ( e.getMessage () ); //IO�G���[��O
		    System.exit ( 0 );
		}
	}
}
