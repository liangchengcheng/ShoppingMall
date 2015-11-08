package cxsjcj.hdsx.com.test;



public class DataManager {

//    private String TAG = getClass().getSimpleName();
//
//    /** ����������ݵ���� */
//    public final int LATEST_INDEX = 0;
//
//    private List mData;
//
//    /** ��һҳ����ʼ�� */
//    private int mNextStart;
//
//    /** ��ݵ�list */
//    private List<PhotoData> mDataList;
//
//    public void loadNewData(final ResponseCallback callback) {
//        loadData(0, callback);
//    }
//
//    public void loadData(final int index, final ResponseCallback callback) {
//        // ����ĵ�ַͷ��
//        final String URL_HEAD
//                = "http://www.duitang.com/napi/blog/list/by_club_id/?club_id=54aa79d9a3101a0f75731c62&limit=0&start=";
//        // ִ����������
//        GsonDecode<Club> mClubDd = new GsonDecode<Club>();
//
//        mClubDd.getGsonData(URL_HEAD + index, Club.class, new Response.Listener<Club>() {
//            @Override
//            public void onResponse(Club club) {
//                Log.d(TAG, "��ȡ�����");
//                mNextStart = club.data.next_start;
//                if (mDataList == null || index == LATEST_INDEX) {
//                    mDataList = club.data.object_list;
//                } else {
//                    mDataList.addAll(club.data.object_list);
//
//                }
//                callback.onSuccess(mDataList);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                callback.onError("time out");
//            }
//        });
//    }
//
//    public List<PhotoData> getData() {
//        return mDataList;
//    }
//
//    public void loadOldData(final ResponseCallback callback) {
//        loadData(mNextStart, callback);
//    }

}
