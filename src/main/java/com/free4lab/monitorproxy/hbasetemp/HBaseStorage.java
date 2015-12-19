package com.free4lab.monitorproxy.hbasetemp;


public class HBaseStorage {
//	private static Logger logger = Logger.getLogger(HBaseStorage.class);
//	private HTablePool pool;
//	
//	/**
//	 * Description: default constructor
//	 * @throws IOException
//	 * @throws InterruptedException
//	 */
//	public HBaseStorage() throws IOException, InterruptedException {
//		pool = new HTablePool(HBaseConfigurationSingleton.getConf(), 10);
//	}
//
//	
//	/**
//	 * Description:
//	 * query by time descending order
//	 * connect to the htable with name of specified 
//	 * create the table if it didn't exist
//	 * @param table
//	 * the table name specified
//	 * @return
//	 * @throws IOException
//	 * @throws InterruptedException
//	 */
//	public HTable getTable(String table) throws IOException, InterruptedException {
//		HTable hTable = null;
//		
//		try {
//			//hTable = new HTable(ConfigurationSingleton.getConf(), table.getBytes());
//			hTable = (HTable) pool.getTable(table);
//			logger.info("successfully connect to htable:" + hTable);
//		} catch (Exception e) {
//			logger.warn(
//					hTable
//							+ "connect failed, try to create table in hbase",
//					e);
//			createTable(table);
//			Thread.sleep(2000);
//			/*hTable = new HTable(ConfigurationSingleton.getConf(),
//					table.getBytes());*/
//			hTable = (HTable) pool.getTable(table);
//		}
//		if (hTable != null) {
//			hTable.setAutoFlush(false);
//			hTable.setWriteBufferSize(8 * 1024 * 1024);
//		}
//		return hTable;
//	}
//
//	/**
//	 * 
//	 * @param tableName 
//	 * @throws IOException
//	 */
//	void createTable(String tableName) throws IOException {
//		logger.info("start creating new table " + tableName);
//		HBaseAdmin admin = getAdmin();
//		for (HTableDescriptor d : admin.listTables()) {
//			logger.info(d.toString());
//		}
//
//		if (admin.tableExists(tableName)) {
//			logger.error("table existed, delete it or use another tablename");
//			return;
//		}
//		
//		//init the table description
//		HTableDescriptor htd = new HTableDescriptor(tableName);
//		HColumnDescriptor family = new HColumnDescriptor(
//				HBaseConstants.FAMILY_LOG.getBytes());
//		HColumnDescriptor props = new HColumnDescriptor(
//				HBaseConstants.FAMILY_PROPS.getBytes());
//		htd.addFamily(family);
//		htd.addFamily(props);
//
//		//create the table
//		admin.createTable(htd);
//		logger.info("successfully creating new table "
//				+ HBaseConstants.TABLENAME);
//	}
//
//	private void deleteTable() throws IOException {
//		HBaseAdmin hbadmin = getAdmin();
//		if(hbadmin.tableExists(HBaseConstants.TABLENAME)) {
//			hbadmin.disableTable(HBaseConstants.TABLENAME);
//			hbadmin.deleteTable(HBaseConstants.TABLENAME);
//		}
//	}
//	
//	/**
//	 * Description: create a object using the configuration 'conf'
//	 * @return
//	 * @throws MasterNotRunningException
//	 * @throws ZooKeeperConnectionException
//	 */
//	HBaseAdmin getAdmin() throws MasterNotRunningException,
//			ZooKeeperConnectionException { 
//		return new HBaseAdmin(HBaseConfigurationSingleton.getConf());
//	}
//	
//	/**
//	 * Description:
//	 * @throws IOException
//	 */
//	public void close() throws IOException {
//		pool.close();
//	}
//
//	/**
//	 * Description: 
//	 * log out all data of the specified data
//	 * @throws IOException
//	 * @throws InterruptedException
//	 */
//	@Deprecated
//	void getAllData() throws IOException, InterruptedException {
//		HTable table = getTable(HBaseConstants.TABLENAME);
//		Scan s = new Scan();
//		ResultScanner rs = table.getScanner(s);
//		for (Result result : rs) {
//			print(result);
//		}
//	}
//
//	public static void print(Result r) {
//		for (KeyValue kv : r.raw()) {
//			logger.info("rowkey:" + new String(kv.getRow()) + " ");
//			logger.info(new String(kv.getFamily()) + ":"
//					+ new String(kv.getQualifier()) + "="
//					+ new String(kv.getValue()));
//		}
//	}
//	
//	@Deprecated
//	public void deleteMessages(byte[] startkey,byte[] endkey) 
//			 throws IOException, InterruptedException{
//        HTable table = getTable(HBaseConstants.TABLENAME);
//        Scan s = new Scan();
//        s.setStartRow(startkey);
//        s.setStopRow(endkey);
//        ResultScanner rs = table.getScanner(s);
//        for (Result result : rs) {
//            Delete delete = new Delete(result.getRow());
//            table.delete(delete);
//        }
//        rs.close();
//    }
//	
//	/**
//	 * 删除顺序表
//	 * @param start
//	 * @param end
//	 * @param tableName
//	 * @param maxTimeSpan 开始时间和结束时间的最大差值,单位为ms
//	 * @throws IOException
//	 * @throws InterruptedException
//	 */
//	private void deleteMessages(String tableName, String topic, long start, long end, long maxTimeSpan) 
//			 throws IOException, InterruptedException{
//        HTable table = getTable(tableName);
//        
//        Scan s = new Scan();
//        ResultScanner rs = null;
//        long endTmp;
//        while(start < end) {
//        	s.setStartRow(getStartRowKey(topic, start));
//        	
//        	if(end - start > maxTimeSpan) {
//        		endTmp = start + maxTimeSpan;
//        		s.setStopRow(getEndRowKey(topic, endTmp));
//        		start = endTmp;
//        	} else {
//        		s.setStopRow(getEndRowKey(topic, end));
//        		start = end;
//        	}
//        	
//        	rs = table.getScanner(s);
//            for (Result result : rs) {
//                Delete delete = new Delete(result.getRow());
//                table.delete(delete);
//            }
//        }
//        rs.close();
//    }
//	
//	/**
//	 * 删除逆序表
//	 * @param startkey
//	 * @param endkey
//	 * @param tableName
//	 * @param maxTimeSpan
//	 * @throws IOException
//	 * @throws InterruptedException
//	 */
//	private void deleteMessagesDesc(String tableName, String topic, long start, long end, int maxTimeSpan) 
//			 throws IOException, InterruptedException{
//		 HTable table = getTable(tableName);
//	        
//	        Scan s = new Scan();
//	        ResultScanner rs = null;
//	        long endTmp;
//	        while(start < end) {
//	        	s.setStopRow(getEndRowKey(topic, -start));
//	        	
//	        	if(end - start > maxTimeSpan) {
//	        		endTmp = start + maxTimeSpan;
//	        		s.setStartRow(getStartRowKey(topic, -endTmp));
//	        		start = endTmp;
//	        	} else {
//	        		s.setStartRow(getStartRowKey(topic, -end));
//	        		start = end;
//	        	}
//	        	
//	        	rs = table.getScanner(s);
//	            for (Result result : rs) {
//	                Delete delete = new Delete(result.getRow());
//	                table.delete(delete);
//	            }
//	        }
//	        rs.close();
//   }
//	
//	protected byte[] getStartRowKey(String topic, long createdTime) {
//		byte[] buf = new byte[48];
//		for (int i = 0; i < 48; i++)
//			buf[i] = 0;
//		int tpl = topic.getBytes().length > 24 ? 24 : topic.getBytes().length;
//		System.arraycopy(topic.getBytes(), 0, buf, 0, tpl);
//		System.arraycopy(BytesUtil.toBytes(createdTime), 0, buf, 24, 8);
//		return buf;
//	}
//
//	protected byte[] getEndRowKey(String topic, long createdTime) {
//		byte[] buf = new byte[48];
//		for (int i = 24; i < 48; i++)
//			buf[i] = (byte) 255;
//		int tpl = topic.getBytes().length > 24 ? 24 : topic.getBytes().length;
//		System.arraycopy(topic.getBytes(), 0, buf, 0, tpl);
//		System.arraycopy(BytesUtil.toBytes(createdTime), 0, buf, 24, 8);
//		return buf;
//	}
//
//	protected byte[] getBiggestTopicEndRowKey(String topic, long createdTime) {
//		byte[] buf = new byte[48];
//		for (int i = 0; i < 24; i++)
//			buf[i] = (byte) 255;
//		for (int i = 24; i < 48; i++)
//			buf[i] = (byte) 255;
//		System.arraycopy(BytesUtil.toBytes(createdTime), 0, buf, 24, 8);
//		return buf;
//	}
//
//	private Map<String, String> toMapString2String(Result r) {
//		Map<String, String> result = new HashMap<String, String>();
//		
//		//default attribute
//		for (Entry<byte[], byte[]> e : r.getFamilyMap(
//				HBaseConstants.FAMILY_LOG.getBytes()).entrySet()) {
//			if (HBaseConstants.LOG_CTIME.equals(new String(e.getKey()))
//					|| HBaseConstants.LOG_OFFSET.equals(new String(e.getKey()))) {
//				//logger.info("get value size is："+e.getValue().length+" ="+printHexString(e.getValue()));
//				result.put(new String(e.getKey()),
//						String.valueOf(BytesUtil.toLong(e.getValue())));
//			} else {
//				result.put(new String(e.getKey()), new String(e.getValue()));
//			}
//		}
//		//Custom Attribute
//		for (Entry<byte[], byte[]> e : r.getFamilyMap(
//				HBaseConstants.FAMILY_PROPS.getBytes()).entrySet()) {
//			result.put(new String(e.getKey()), new String(e.getValue()));
//		}
//		return result;
//	}
//
//	
//	// 将指定byte数组以16进制的形式打印到控制台
//	public static String printHexString(byte[] b) {
//		String result = "";
//		for (int i = 0; i < b.length; i++) {
//			String hex = Integer.toHexString(b[i] & 0xFF);
//			if (hex.length() == 1) {
//				hex = '0' + hex;
//			}
//			// System.out.print(hex.toUpperCase() );
//			result += hex.toUpperCase();
//		}
//		return result;
//	}
//		
//		
//	/**
//	 * Description:
//	 * query the Hbase and return results of specfied size
//	 * by the given query conditions 
//	 * 
//	 * @param searchCriteria
//	 * Map of query param
//	 * @param topic
//	 * @param starttime
//	 * @param endtime
//	 * the three params above to generate the range of rowKey
//	 * @param resultSize
//	 * the limit size of result
//	 * @return
//	 * @throws IOException
//	 * @throws InterruptedException
//	 */
//	public List<Map<String, String>> scanMessage(String topic,
//			Map<String, String> searchCriteria, Timestamp starttime,
//			Timestamp endtime, Integer resultSize) throws IOException,
//			InterruptedException {
//		//specify the start key and end key
//		Scan s = new Scan();
//		s.setCaching(resultSize);
//		if (StringUtil.isBlank(topic)) {
//			s.setStartRow(getStartRowKey(topic, starttime.getTime()));
//			s.setStopRow(getBiggestTopicEndRowKey(topic, endtime.getTime()));
//		} else {
//			s.setStartRow(getStartRowKey(topic, starttime.getTime()));
//			s.setStopRow(getEndRowKey(topic, endtime.getTime()));
//		}
//		
//		//specify the query param
//		List<Filter> filters = new ArrayList<Filter>();
//		if (searchCriteria != null && searchCriteria.size() > 0) {
//			for (String at : searchCriteria.keySet()) {
//				if (!StringUtil.isBlank(searchCriteria.get(at))) {
//					if (LogPro.isInDefaultPros(at)) {
//						if(HBaseConstants.LOG_OFFSET.equals(at)) {
//							long tempOffset = 0;
//							try {
//								tempOffset = Long.valueOf(searchCriteria.get(at));
//							} catch (Exception e) {
//								logger.warn("offset "+searchCriteria.get(at)+" cannot convert to long");
//							}
//							SingleColumnValueFilter scvf = new SingleColumnValueFilter(
//									Bytes.toBytes(HBaseConstants.FAMILY_LOG),
//									Bytes.toBytes(at), CompareOp.EQUAL,
//									Bytes.toBytes(tempOffset));
//							filters.add(scvf);
//							
//						} else {
//							DependentColumnFilter dcf = new DependentColumnFilter(
//									Bytes.toBytes(HBaseConstants.FAMILY_LOG),
//									Bytes.toBytes(at), false, CompareOp.EQUAL,
//									new SubstringComparator(searchCriteria.get(at)));
//							filters.add(dcf);
//						}
//					} else {
//						if(at.equals(ProExtraUtils.USER_ID)) {
//							SingleColumnValueFilter scvf =
//									new SingleColumnValueFilter(
//											Bytes.toBytes(HBaseConstants.FAMILY_PROPS), 
//											Bytes.toBytes(at), CompareOp.EQUAL,
//										Bytes.toBytes(searchCriteria.get(at)));
//							filters.add(scvf);
//						} else {
//							DependentColumnFilter dcf = new DependentColumnFilter(
//									Bytes.toBytes(HBaseConstants.FAMILY_PROPS),
//									Bytes.toBytes(at), false, CompareOp.EQUAL,
//									new SubstringComparator(searchCriteria.get(at)));
//							filters.add(dcf);
//						}
//					}
//
//				}
//			}
//
//			if (filters.size() > 0) {
//				FilterList filterList = new FilterList(filters);
//				s.setFilter(filterList);
//			}
//		}
//
//		HTable table = getTable(HBaseConstants.TABLENAME);
//		s.setCacheBlocks(false);
//
//		ResultScanner rs = table.getScanner(s);
//		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
//		int num=0;
//		for (Result r : rs) {
//			if (r == null)
//				continue;
//			resultList.add(toMapString2String(r));
//			num++;
//			if(num >=resultSize )break;
//		}
//		
//		table.close();
//		return resultList;
//	}
//	
//	
//	public List<Message> scanMessage(Message m,long endtime,int length) throws IOException, InterruptedException{
//        return doScan(getRowKey(m.getTopic(),m.getCreatedTime(),m.getIpAddress(),m.getSource(),m.getOffset()+1),
//                getEndRowKey(m.getTopic(),endtime),
//                length);
//    }
//	
//	private List<Message> doScan(byte[] startkey , byte[] endkey , int length) throws IOException, InterruptedException{
//        HTable table = getTable(HBaseConstants.TABLENAME);
//        Scan s = new Scan();
//        s.setCaching(10);
//        s.setStartRow(startkey);
//        s.setStopRow(endkey);
//        ResultScanner rs = table.getScanner(s);
//        List<Message> messages = new ArrayList<Message>(length);
//        int num = 0;
//        for (Result result : rs) {
//            messages.add(toMessage(result));
//            num++;
//            if(num >=length )break;
//        }
//        rs.close();
//        table.close();
//        return messages;
//    }
//	
//	public Message toMessage(Result r){
//        String topic = new String(r.getValue(HBaseConstants.FAMILY_LOG.getBytes(), 
//                HBaseConstants.LOG_TOPIC.getBytes()));
//        byte[] content =  r.getValue(HBaseConstants.FAMILY_LOG.getBytes(), 
//                HBaseConstants.LOG_CONTENT.getBytes());
//        String ipAddress = new String(r.getValue(HBaseConstants.FAMILY_LOG.getBytes(), 
//                HBaseConstants.LOG_IPADDRESS.getBytes()));
//        String source = new String(r.getValue(HBaseConstants.FAMILY_LOG.getBytes(), 
//                HBaseConstants.LOG_SOURCE.getBytes()));
//        long createdTime = BytesUtil.toLong(r.getValue(HBaseConstants.FAMILY_LOG.getBytes(), 
//                HBaseConstants.LOG_CTIME.getBytes()));
//        long offset = BytesUtil.toLong(r.getValue(HBaseConstants.FAMILY_LOG.getBytes(), 
//                HBaseConstants.LOG_OFFSET.getBytes()));
//        
//        Map<String, String> props = new HashMap<String, String>();
//        for (Entry<byte[], byte[]> e : r.getFamilyMap(HBaseConstants.FAMILY_PROPS.getBytes()).entrySet()) {
//            props.put(new String(e.getKey()), new String(e.getValue()));
//        }
//        
//        Message m = MessageFactory.getInstance().createMessage(topic, content, ipAddress, source, createdTime, offset, props);
//        return m;
//    }
//	
//	/**
//	 * 
//	 * @param topic
//	 * @param searchCriteria
//	 * @param starttime
//	 * @param endtime
//	 * @param resultSize
//	 * @return
//	 * @throws IOException
//	 * @throws InterruptedException
//	 */
//	public List<Map<String, String>> scanMessageTimeDesc(String topic,
//			Map<String, String> searchCriteria, Timestamp starttime,
//			Timestamp endtime, Integer resultSize) throws IOException,
//			InterruptedException {
//		Scan s = new Scan();
//		s.setCaching(resultSize);
//		if (StringUtil.isBlank(topic)) {
//			s.setStartRow(getStartRowKey(topic, -starttime.getTime()));
//			s.setStopRow(getBiggestTopicEndRowKey(topic, -endtime.getTime()));
//		} else {
//			s.setStartRow(getStartRowKey(topic, -starttime.getTime()));
//			s.setStopRow(getEndRowKey(topic, -endtime.getTime()));
//		}
//
//		List<Filter> filters = new ArrayList<Filter>();
//		if (searchCriteria != null && searchCriteria.size() > 0) {
//			for (String at : searchCriteria.keySet()) {
//				if (!StringUtil.isBlank(searchCriteria.get(at))) {
//					if (LogPro.isInDefaultPros(at)) {
//						if(HBaseConstants.LOG_OFFSET.equals(at)) {
//							long tempOffset = 0;
//							try {
//								tempOffset = Long.valueOf(searchCriteria.get(at));
//							} catch (Exception e) {
//								logger.warn("offset "+searchCriteria.get(at)+" cannot convert to long");
//							}
//							SingleColumnValueFilter scvf = new SingleColumnValueFilter(
//									Bytes.toBytes(HBaseConstants.FAMILY_LOG),
//									Bytes.toBytes(at), CompareOp.EQUAL,
//									Bytes.toBytes(tempOffset));
//							filters.add(scvf);
//							
//						} else {
//							DependentColumnFilter dcf = new DependentColumnFilter(
//									Bytes.toBytes(HBaseConstants.FAMILY_LOG),
//									Bytes.toBytes(at), false, CompareOp.EQUAL,
//									new SubstringComparator(searchCriteria.get(at)));
//							filters.add(dcf);
//						}
//					} else {
//						if(at.equals(ProExtraUtils.USER_ID)) {
//							SingleColumnValueFilter scvf =
//			        	        	new SingleColumnValueFilter(
//			        	        			Bytes.toBytes(HBaseConstants.FAMILY_PROPS), 
//			        	        			Bytes.toBytes(at), CompareOp.EQUAL,
//			        	        		Bytes.toBytes(searchCriteria.get(at)));
//							filters.add(scvf);
//						} else {
//							DependentColumnFilter dcf = new DependentColumnFilter(
//									Bytes.toBytes(HBaseConstants.FAMILY_PROPS),
//									Bytes.toBytes(at), false, CompareOp.EQUAL,
//									new SubstringComparator(searchCriteria.get(at)));
//							filters.add(dcf);
//						}
//					}
//
//				}
//			}
//
//			if (filters.size() > 0) {
//				FilterList filterList = new FilterList(filters);
//				s.setFilter(filterList);
//			}
//		}
//
//		HTable table = getTable(HBaseConstants.TABLENAME_TIME_DESC);
//		s.setCacheBlocks(false);
//		
//		ResultScanner rs = table.getScanner(s);
//		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
//		int num=0;
//		for (Result r : rs) {
//			if (r == null)
//				continue;
//			resultList.add(toMapString2String(r));
//			num++;
//            if(num >=resultSize )break;
//		}
//		table.close();
//		return resultList;
//	}
//	
//	protected static byte[] getRowKey(Message m){
//        return getRowKey(m.getTopic(),m.getCreatedTime(),m.getIpAddress(),m.getSource(),m.getOffset());
//    }
//	
//	static byte[] getRowKey(String topic,long createdTime,String ipAddress,String source,long offset){
//        byte[] buf = new byte[48];
//        int tpl = topic.getBytes().length > 24 ?24 : topic.getBytes().length;
//        System.arraycopy(topic.getBytes(), 0, buf, 0, tpl);
//        System.arraycopy(BytesUtil.toBytes(createdTime), 0, buf, 24, 8);
//        System.arraycopy(BytesUtil.toBytes(ipAddress.hashCode()), 0, buf, 32, 4);
//        System.arraycopy(BytesUtil.toBytes(source.hashCode()), 0, buf, 36, 4);
//        System.arraycopy(BytesUtil.toBytes(offset), 0, buf, 40, 8);
//        return buf;
//    }
//	
//	public static Put getPut(Message m)  {
//        Put newput = new Put(getRowKey(m));
//		
//        newput.add(HBaseConstants.FAMILY_LOG.getBytes(),
//        		HBaseConstants.LOG_TOPIC.getBytes(),
//                m.getTopic().getBytes());
//        newput.add(HBaseConstants.FAMILY_LOG.getBytes(),
//        		HBaseConstants.LOG_CONTENT.getBytes(),
//                m.getContent());
//        newput.add(HBaseConstants.FAMILY_LOG.getBytes(),
//        		HBaseConstants.LOG_CTIME.getBytes(),
//                BytesUtil.toBytes(m.getCreatedTime()));
//        newput.add(HBaseConstants.FAMILY_LOG.getBytes(),
//        		HBaseConstants.LOG_IPADDRESS.getBytes(),
//                m.getIpAddress().getBytes());
//        newput.add(HBaseConstants.FAMILY_LOG.getBytes(),
//        		HBaseConstants.LOG_SOURCE.getBytes(),
//                m.getSource().getBytes());
//        newput.add(HBaseConstants.FAMILY_LOG.getBytes(),
//        		HBaseConstants.LOG_OFFSET.getBytes(),
//                BytesUtil.toBytes(m.getOffset()));
//        for (Entry<String, String> e : m.getAllProperties().entrySet()) {
//            newput.add(HBaseConstants.FAMILY_PROPS.getBytes(),
//                    e.getKey().getBytes(), 
//                    e.getValue().getBytes());
//        }
//        
//        return newput;
//    }
//
//	
//	
//	public static void main(String[] args) throws IOException,
//			InterruptedException, JSONException, ParseException {
//	}
}
