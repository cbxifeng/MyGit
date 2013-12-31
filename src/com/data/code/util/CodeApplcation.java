package com.data.code.util;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class CodeApplcation extends Application {
	public static CodeApplcation codeApplcation = null;
	public static BMapManager bMapManager = null;  
	public boolean m_bKeyRight = false;
	public static String key = "86E297E0a9e2d2ccba5692c3c9010f67";

	MyLocationListener listener = new MyLocationListener();

	public static String address;
	public static Double longitude;// ����
	public static Double latitude;// ά��
	LocationClient locationClient;// ��λ���
	LocationData locationData;// ��λ����

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		codeApplcation = this;

		initEngineManager(this);

		locationClient = new LocationClient(this);
		locationData = new LocationData();
		locationClient.registerLocationListener(listener);
		LocationClientOption locationClientOption = new LocationClientOption();
		locationClientOption.setOpenGps(true);// ��gps
		locationClientOption.setCoorType("bd09ll");// ����������ʽ
		locationClientOption.setPriority(LocationClientOption.NetWorkFirst);
		locationClientOption.setAddrType("all");// �򿪵�ַ����
		locationClientOption.setScanSpan(5000);// ˢ��
		locationClient.setLocOption(locationClientOption);
		locationClient.start();
		locationClient.requestLocation();

		super.onCreate();
	}

	public static CodeApplcation getInstance() {
		return codeApplcation;

	}

	public void initEngineManager(Context context) {
		if (bMapManager == null) {
			bMapManager = new BMapManager(context);
		}
		bMapManager.init(key, new MyGeneralListener() { 
		});
	}

	public static class MyGeneralListener implements MKGeneralListener {

		public void onGetNetworkState(int arg0) {
			// TODO Auto-generated method stub

		}

		public void onGetPermissionState(int iError) {
			// TODO Auto-generated method stub
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// ��ȨKey����
				Toast.makeText(
						CodeApplcation.getInstance().getApplicationContext(),
						"���� DemoApplication.java�ļ�������ȷ����ȨKey��",
						Toast.LENGTH_LONG).show();
				CodeApplcation.getInstance().m_bKeyRight = false;
			}
		}

	}

	private class MyLocationListener implements BDLocationListener {

		@SuppressWarnings("unused")
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub

			if (location == null) {
				return;
			}

			latitude = location.getLatitude();// γ��
			//locationData.latitude=location.getLatitude();
			longitude = location.getLongitude();// ����
			//locationData.longitude=location.getLongitude();
//			MKSearch search = new MKSearch();
//			
//			search.reverseGeocode(new GeoPoint((int)(locationData.longitude), (int)(locationData.latitude)));
//			
//            search.init(bMapManager, new MyMKSearchListener());
            
            
            
			Log.i("latitude", "" + locationData.longitude+"fff"+locationData.longitude*1e6);
			Log.i("�ж�", "" + location.getLocType());
			if (location.getLocType() == BDLocation.TypeNetWorkLocation) {

				address = location.getAddrStr();
				Log.i("������", "" + address);
			}

		}

		public void onReceivePoi(BDLocation popLocation) {
			// TODO Auto-generated method stub
			if (popLocation == null) {
				return;
			}
		}

	}
	class MyMKSearchListener implements MKSearchListener{

		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
			// TODO Auto-generated method stub
			 if (arg0 == null) {
				 address="û�л�ȡ��Ҫ��λ��";
	            } else {
	                GeoPoint point = arg0.geoPt;
	                address= arg0.strAddr.toString();
	                Log.i("��ȡ", "" + address);
	            }
			
		}

		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {
			// TODO Auto-generated method stub
			
		}

		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
		
	} 
}
