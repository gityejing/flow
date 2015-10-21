/**
 * @(#)LongArrayHandler.java 2007-7-28
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-7-28
 * @since       JDK1.5
 */

public class LongArrayHandler implements ResultSetHandler {

	public Object handle(ResultSet rs) throws SQLException {
		List tmp = new ArrayList();

		while(rs.next()){
			tmp.add(new Long(rs.getLong(1)));
		}
		
		long[] longs = new long[tmp.size()];

		for(int i=0;i<longs.length;i++){
			longs[i] = ((Long)tmp.get(i)).longValue();
		}
		
		return longs;
	}

}
