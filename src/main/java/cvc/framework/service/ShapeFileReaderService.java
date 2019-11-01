/**
 * diewald_shapeFileReader.
 * 
 * a Java Library for reading ESRI-shapeFiles (*.shp, *.dfb, *.shx).
 * 
 * 
 * Copyright (c) 2012 Thomas Diewald
 *
 *
 * This source is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This code is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * A copy of the GNU General Public License is available on the World
 * Wide Web at <http://www.gnu.org/copyleft/gpl.html>. You can also
 * obtain it by writing to the Free Software Foundation,
 * Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */


package cvc.framework.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class ShapeFileReaderService {

  public List<List<double[]>> getPoints() {
    DBF_File.LOG_INFO           = !false;
    DBF_File.LOG_ONLOAD_HEADER  = false;
    DBF_File.LOG_ONLOAD_CONTENT = false;
    
    SHX_File.LOG_INFO           = !false;
    SHX_File.LOG_ONLOAD_HEADER  = false;
    SHX_File.LOG_ONLOAD_CONTENT = false;
    
    SHP_File.LOG_INFO           = !false;
    SHP_File.LOG_ONLOAD_HEADER  = false;
    SHP_File.LOG_ONLOAD_CONTENT = false;
    List<List<double[]>> res=new LinkedList<>();

    try {
      // GET DIRECTORY
      String curDir = System.getProperty("user.dir");
      String folder = "/data/";
      // LOAD SHAPE FILE (.shp, .shx, .dbf)--读完这三个文件
      ShapeFile shapefile = new ShapeFile(curDir+folder, "china").READ();
      // TEST: printing some content
      ShpShape.Type shape_type = shapefile.getSHP_shapeType();
      System.out.println("\nshape_type = " +shape_type);
    
      int number_of_shapes = shapefile.getSHP_shapeCount();
      int number_of_fields = shapefile.getDBF_fieldCount();
  
      for(int i = 0; i < number_of_shapes; i++){
        ShpPolygon shape    = shapefile.getSHP_shape(i);
        String[] shape_info = shapefile.getDBF_record(i);
  
        ShpShape.Type type     = shape.getShapeType();
        int number_of_vertices = shape.getNumberOfPoints();
        int number_of_polygons = shape.getNumberOfParts();
        int record_number      = shape.getRecordNumber();
        
        System.out.printf("\nSHAPE[%2d] - %s\n", i, type);
        System.out.printf("  (shape-info) record_number = %3d; vertices = %6d; polygons = %2d\n", record_number, number_of_vertices, number_of_polygons);
        
        for(int j = 0; j < number_of_fields; j++){
          String data = shape_info[j].trim();
          DBF_Field field = shapefile.getDBF_field(j);
          String field_name = field.getName();
          System.out.printf("  (dbase-info) [%d] %s = %s", j, field_name, data);
        }
        System.out.printf("\n");
        int polygonLen=shape.getSHP_parts().length,pointLen=shape.getPoints().length;
        //按照多边形为单位添加点的集合到列表中--一个shape可能有几个多边形
        for(int j=0;j<polygonLen;j++) {
        	List<double[]> tempPolygon=new LinkedList<>();
        	for(int k=shape.getSHP_parts()[j];k<(j==polygonLen-1?pointLen:shape.getSHP_parts()[j+1]);k++) {
        		tempPolygon.add(shape.getPoints()[k]);
        	}
        	res.add(tempPolygon);
        }
      }
      //只绘画点-由一个点组成的一个shape
      shapefile = new ShapeFile(curDir+folder, "poi").READ();
      // TEST: printing some content
      shape_type = shapefile.getSHP_shapeType();
      System.out.println("\nshape_type = " +shape_type);
    
      number_of_shapes = shapefile.getSHP_shapeCount();
      number_of_fields = shapefile.getDBF_fieldCount();
  
      for(int i = 0; i < number_of_shapes; i++){
        ShpPoint shape    = shapefile.getSHP_shape(i);
        String[] shape_info = shapefile.getDBF_record(i);
  
        ShpShape.Type type     = shape.getShapeType();
        System.out.printf("\nSHAPE[%2d] - %s\n", i, type);
        
        for(int j = 0; j < number_of_fields; j++){
          String data = shape_info[j].trim();
          System.out.println(data);
          DBF_Field field = shapefile.getDBF_field(j);
          String field_name = field.getName();
          //System.out.printf("  (dbase-info) [%d] %s = %s", j, field_name, data);
        }
        System.out.printf("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return res;
  }
  public static void main(String[] args) {
	    DBF_File.LOG_INFO           = !false;
	    DBF_File.LOG_ONLOAD_HEADER  = false;
	    DBF_File.LOG_ONLOAD_CONTENT = false;
	    
	    SHX_File.LOG_INFO           = !false;
	    SHX_File.LOG_ONLOAD_HEADER  = false;
	    SHX_File.LOG_ONLOAD_CONTENT = false;
	    
	    SHP_File.LOG_INFO           = !false;
	    SHP_File.LOG_ONLOAD_HEADER  = false;
	    SHP_File.LOG_ONLOAD_CONTENT = false;
	    

	    try {
	      // GET DIRECTORY
	      String curDir = System.getProperty("user.dir");
	      String folder = "/data/";
	      
	      // LOAD SHAPE FILE (.shp, .shx, .dbf)--读完这三个文件
	      ShapeFile shapefile = new ShapeFile(curDir+folder, "poi").READ();
	      
	      // TEST: printing some content
	      ShpShape.Type shape_type = shapefile.getSHP_shapeType();
	      System.out.println("\nshape_type = " +shape_type);
	    
	      int number_of_shapes = shapefile.getSHP_shapeCount();
	      int number_of_fields = shapefile.getDBF_fieldCount();
	  
	      for(int i = 0; i < number_of_shapes; i++){
	        ShpPoint shape    = shapefile.getSHP_shape(i);
	        String[] shape_info = shapefile.getDBF_record(i);
	  
	        ShpShape.Type type     = shape.getShapeType();
	        int record_number      = shape.getRecordNumber();
	        
	        System.out.printf("\nSHAPE[%2d] - %s\n", i, type);
	        
	        for(int j = 0; j < number_of_fields; j++){
	          String data = shape_info[j].trim();
	          DBF_Field field = shapefile.getDBF_field(j);
	          String field_name = field.getName();
	          System.out.printf("  (dbase-info) [%d] %s = %s", j, field_name, data);
	        }
	        System.out.printf("\n");
	      }
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
}
