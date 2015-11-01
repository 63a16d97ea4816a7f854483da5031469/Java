package jdbc;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.beans.*;

 /**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

 /**
 * 类DBResult是对数据库查询所得的结果集处理，使结果集存储在一个HashMap中，不能实时更新
 */
 public class DBResult implements Serializable{
    private HashMap hashMap_;  //存储所得的结果集
    private int currentPos_;  //当前停留在第几行记录，范围为[0,rows-1]
    private int rows_;//返回的记录行数
    private int columns_;//列数
    private ArrayList columnNames_; //存储字段名

    private class DateTime {
        public java.sql.Date date;
        public Time time;
        public Timestamp timeStamp;
    }

    /**
    * 构造函数初始化各私有变量
    */
    public DBResult(){
        hashMap_ = new HashMap();
        columnNames_ = new ArrayList();
        currentPos_ = -1;
        rows_ = 0;
        columns_ = 0;
    }


    /**
    *对数据库返回的结果集进行处理
    *
    * @param rs 数据库返回的结果集
    * @exception SQLException 对结果集操作失败
    */
    public void create(ResultSet rs) throws SQLException{
        ResultSetMetaData meta_data = rs.getMetaData();
        columns_ = meta_data.getColumnCount();
        int size = 0;
        ArrayList[] values = new ArrayList[columns_];
        for(int i=0; i<columns_; i++){
            values[i] = new ArrayList();
        }
        while(rs.next()){
            for(int i=0; i<columns_; i++){
                int type = meta_data.getColumnType(i+1);
                switch(type){
                    case Types.CHAR:
                    case Types.VARCHAR:
                        String string_value = rs.getString(i+1);
                        values[i].add(string_value);
                        break;
                    case Types.INTEGER:
                    case Types.BIT:
                        int int_value = rs.getInt(i+1);
                        values[i].add(new Integer(int_value));
                        break;
                    case Types.FLOAT:
                        float float_value = rs.getFloat(i+1);
                        values[i].add(new Float(float_value));
                        break;
                    case Types.DATE:
                        DateTime date_time1 = new DateTime();
                        date_time1.date = rs.getDate(i+1);
                        date_time1.time = null;
                        date_time1.timeStamp = null;
                        values[i].add(date_time1);
                        break;
                    case Types.TIME:
                        DateTime date_time2 = new DateTime();
                        date_time2.date = null;
                        date_time2.time = rs.getTime(i+1);
                        date_time2.timeStamp = null;
                        values[i].add(date_time2);
                        break;
                    case Types.TIMESTAMP:
                        DateTime date_time3 = new DateTime();
                        date_time3.timeStamp = rs.getTimestamp(i+1);
                        if(date_time3.timeStamp==null){
                            date_time3.date = null;
                            date_time3.time = null;
                        }
                        else {
                            date_time3.date = new java.sql.Date(date_time3.timeStamp.getTime());
                            date_time3.time = new Time(date_time3.timeStamp.getTime());
                        }
                        values[i].add(date_time3);
                        break;
                    default :
                        String default_value = rs.getString(i+1);
                    values[i].add(default_value);
                }
            }
            size ++;
        }

        for(int i=0; i<columns_; i++){
            String name = meta_data.getColumnName(i+1).toUpperCase();
            columnNames_.add(name);
            hashMap_.put(name,values[i]);
        }
        rows_ = size;
    }

    /**
    *在所得的HashMap中取int型的字段
    *
    * @param columnName 所取的字段名
    * @return int_value 取得的字段值
    * @exception Exception 操作失败
    */
    public int getInt(String columnName) throws Exception{
        columnName = columnName.toUpperCase();
        if(!columnNames_.contains(columnName)){
            throw new Exception("DBResult.getInt:没有这一列!");
        }
        if(currentPos_<0||currentPos_>rows_-1){
            throw new Exception("DBResult.getInt:行数越界!");
        }
        ArrayList current_row = (ArrayList)hashMap_.get(columnName);
        Integer int_value = null;
        try {
          int_value = new Integer((String)current_row.get(currentPos_));
        }
        catch (Exception ex) {
        }
        return int_value.intValue();
    }

    /**
    *在所得的HashMap中取int型的字段
    *
    * @param columnNumber 所取的字段序列数,范围为[1,columns_]
    * @return  取得的字段值
    * @exception Exception 操作失败
    */
    public int getInt(int columnNumber) throws Exception{
        if(columnNumber<1||columnNumber>columns_){
            throw new Exception("DBResult.getInt:行数越界!");
        }
        return getInt((String)columnNames_.get(columnNumber-1));
    }

    /**
    *在所得的HashMap中取float型的字段
    *
    * @param columnName 所取的字段名
    * @return float_value 取得的字段值
    * @exception Exception 操作失败
    */
    public float getFloat(String columnName) throws Exception{
        columnName = columnName.toUpperCase();
        if(!columnNames_.contains(columnName)){
            throw new Exception("DBResult.getFloat:没有这一列!");
        }
        if(currentPos_<0||currentPos_>rows_-1){
            throw new Exception("DBResult.getFloat:行数越界!");
        }
        ArrayList current_row = (ArrayList)hashMap_.get(columnName);
        Float float_value = (Float)current_row.get(currentPos_);
        return float_value.floatValue();
    }

    /**
    *在所得的HashMap中取float型的字段
    *
    * @param columnNumber 所取的字段序列数,范围为[1,columns_]
    * @return  取得的字段值
    * @exception Exception 操作失败
    */
    public float getFloat(int columnNumber) throws Exception{
        if(columnNumber<1||columnNumber>columns_){
            throw new Exception("DBResult.getInt:行数越界!");
        }
        return getFloat((String)columnNames_.get(columnNumber-1));
    }

    /**
    *在所得的HashMap中取String型的字段
    *
    * @param columnName 所取的字段名
    * @return string_value 取得的字段值
    * @exception Exception 操作失败
    */
    public String getString(String columnName) throws Exception{
        columnName = columnName.toUpperCase();
        if(!columnNames_.contains(columnName)){
            throw new Exception("DBResult.getString:没有这一列!");
        }
        if(currentPos_<0||currentPos_>rows_-1){
            throw new Exception("DBResult.getString:行数越界!");
        }
        ArrayList current_row = (ArrayList)hashMap_.get(columnName);
        String string_value = (String)current_row.get(currentPos_);
        if(string_value==null)
            string_value = "";
        return string_value;
    }

    /**
    *在所得的HashMap中取String型的字段
    *
    * @param columnNumber 所取的字段序列数,范围为[1,columns_]
    * @return  取得的字段值
    * @exception Exception 操作失败
    */
    public String getString(int columnNumber) throws Exception{
        if(columnNumber<1||columnNumber>columns_){
            throw new Exception("DBResult.getInt:行数越界!");
        }
        return getString((String)columnNames_.get(columnNumber-1));
    }

    /**
    *在所得的HashMap中取java.sql.Date型的字段
    *
    * @param columnName 所取的字段名
    * @return date_value 取得的字段值
    * @exception Exception 操作失败
    */
    public java.sql.Date getDate(String columnName) throws Exception{
        columnName = columnName.toUpperCase();
        if(!columnNames_.contains(columnName)){
            throw new Exception("DBResult.getDate:没有这一列!");
        }
        if(currentPos_<0||currentPos_>rows_-1){
            throw new Exception("DBResult.getDate:行数越界!");
        }
        ArrayList current_row = (ArrayList)hashMap_.get(columnName);
        DateTime date_time = (DateTime)current_row.get(currentPos_);
        if(date_time==null)
           return null;
        java.sql.Date date_value = date_time.date;
        return date_value;
    }

    /**
    *在所得的HashMap中取java.sql.Date型的字段
    *
    * @param columnNumber 所取的字段序列数,范围为[1,columns_]
    * @return  取得的字段值
    * @exception Exception 操作失败
    */
    public java.sql.Date getDate(int columnNumber) throws Exception{
        if(columnNumber<1||columnNumber>columns_){
            throw new Exception("DBResult.getDate:行数越界!");
        }
        return getDate((String)columnNames_.get(columnNumber-1));
    }

    /**
    *在所得的HashMap中取Time型的字段
    *
    * @param columnName 所取的字段名
    * @return time_value 取得的字段值
    * @exception Exception 操作失败
    */
    public Time getTime(String columnName) throws Exception{
        columnName = columnName.toUpperCase();
        if(!columnNames_.contains(columnName)){
            throw new Exception("DBResult.getTime:没有这一列!");
        }
        if(currentPos_<0||currentPos_>rows_-1){
            throw new Exception("DBResult.getTime:行数越界!");
        }
        ArrayList current_row = (ArrayList)hashMap_.get(columnName);
        DateTime date_time = (DateTime)current_row.get(currentPos_);
        Time time_value = date_time.time;
        return time_value;
    }

    /**
    *在所得的HashMap中取Time型的字段
    *
    * @param columnNumber 所取的字段序列数,范围为[1,columns_]
    * @return  取得的字段值
    * @exception Exception 操作失败
    */
    public Time getTime(int columnNumber) throws Exception{
        if(columnNumber<1||columnNumber>columns_){
            throw new Exception("DBResult.getTime:行数越界!");
        }
        return getTime((String)columnNames_.get(columnNumber-1));
    }

    /**
    *移到下一条记录
    *
    * @return  操作成功与否
    */
    public boolean next(){
        if(currentPos_<rows_-1){
            currentPos_++;
            return true;
        }
        else {
            return false;
        }
    }

    /**
    *移到前一条记录
    *
    * @return  操作成功与否
    */
    public boolean previous(){
        if(currentPos_ != -1){
            currentPos_--;
            return true;
        }
        else {
            return false;
        }
    }

    /**
    *移到第一条记录
    */
    public void first(){
        currentPos_ = 0;
    }

    /**
    *移到最后一条记录
    */
    public void last(){
        currentPos_ = rows_ - 1;
    }

    /**
    *移到指定的记录行
    *
    * @param rowNumber 指定的行数
    * @return  操作成功与否
    */
    public boolean moveTo(int rowNumber){
        if(rowNumber>=0&&rowNumber<=rows_-1){
            currentPos_ = rowNumber;
            return true;
        }
        else {
            return false;
        }
    }

    /**
    *返回记录数
    *
    * @return  rows_ 记录数
    */
    public int getRows(){
        return rows_;
    }

    /**
    *在所得的HashMap中取Timestamp型的字段
    *
    * @param columnName 所取的字段名
    * @return time_value 取得的字段值
    * @exception Exception 操作失败
    */
    public Timestamp getTimestamp(String columnName) throws Exception{
        columnName = columnName.toUpperCase();
        if(!columnNames_.contains(columnName)){
            throw new Exception("DBResult.getTime:没有这一列!");
        }
        if(currentPos_<0||currentPos_>rows_-1){
            throw new Exception("DBResult.getTime:行数越界!");
        }
        ArrayList current_row = (ArrayList)hashMap_.get(columnName);
        DateTime date_time = (DateTime)current_row.get(currentPos_);
        Timestamp time_value = date_time.timeStamp;
        return time_value;
    }

    /**
    *在所得的HashMap中取Timestamp型的字段
    *
    * @param columnNumber 所取的字段序列数,范围为[1,columns_]
    * @return  取得的字段值
    * @exception Exception 操作失败
    */
    public Timestamp getTimestamp(int columnNumber) throws Exception{
        if(columnNumber<1||columnNumber>columns_){
            throw new Exception("DBResult.getTime:行数越界!");
        }
        return getTimestamp((String)columnNames_.get(columnNumber-1));
    }
}
