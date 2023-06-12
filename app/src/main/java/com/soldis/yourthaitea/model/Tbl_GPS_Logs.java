package com.soldis.yourthaitea.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ftctest on 20/10/2017.
 */

public class Tbl_GPS_Logs{
    public List<TblGPSLogs> m_data = null;

    public class TblGPSLogs
    {
        public String TIME_LOG;
        public String CUSTNO;
        public String LATITUDE;
        public String LONGITUDE;
        public String SPEED;
        public int BATTERY_LEVEL;

        public TblGPSLogs(String TIME_LOG,String CUSTNO,String LATITUDE, String LONGITUDE, String SPEED, int BATTERY_LEVEL)
        {
            this.TIME_LOG = TIME_LOG;
            this.CUSTNO = CUSTNO;
            this.LATITUDE = LATITUDE;
            this.LONGITUDE = LONGITUDE;
            this.SPEED = SPEED;
            this.BATTERY_LEVEL = BATTERY_LEVEL;
        }
    }

    public void AddData(TblGPSLogs data)
    {
        if (m_data == null)
            m_data =  new ArrayList<>();

        m_data.add(data);
    }

    public void DeleteRecording(int itemNumber)
    {
        if ( (itemNumber < 0) || (m_data.size() <= itemNumber))
            return;

        m_data.remove(itemNumber);
    }
}
