package com.maven.flow.util;
//JSONEncoder 类提供了编码文字、对象和数组的方法。结果存储在 java.lang.StringBuilder 中
/*关于 String[ALL],StringBuffer[ALL],StringBuilder[JDK1.50]的速度
StringBuilder>StringBuffer>String;
但执行单个String 操作时,用String还是比较快的
*/
public class JSONEncoder {
  private StringBuilder buf;
  //private StringBuffer buf;
  public JSONEncoder() {
    buf = new StringBuilder();
    //buf = new StringBuffer();
  }
 //character() 方法编码单一字符
  public void character(char ch) {
    switch (ch) {
      case '\'':
      case '\"':
      case '\\':
        buf.append('\\');
        buf.append(ch);
        break;
      case '\t':
        buf.append('\\');
        buf.append('t');
        break;
      case '\r':
        buf.append('\\');
        buf.append('r');
        break;
      case '\n':
        buf.append('\\');
        buf.append('n');
        break;
      default:
        if (ch >= 32 && ch < 128) {
          buf.append(ch);
        }
        else {
          buf.append('\\');
          buf.append('u');
          for (int j = 12; j >= 0; j -= 4) {
            int k = ( ( (int) ch) >> j) & 0x0f;
            int c = k < 10 ? '0' + k : 'a' + k - 10;
            buf.append( (char) c);
          }
        }
    }
  }
 //string() 方法编码整个字符串
  public void string(String str) {
  int length = str.length();
  for (int i = 0; i < length; i++)
  character(str.charAt(i));
  }
  //literal() 方法编码 JavaScript 文字
  public void literal(Object value) {
  if (value instanceof String) {
  buf.append('"');
  string((String) value);
  buf.append('"');
  } else if (value instanceof Character) {
  buf.append('\'');
  character(((Character) value).charValue());
  buf.append('\'');
  } else
  buf.append(value.toString());
  }
  //comma() 方法附加一个逗号字符
  private void comma() {
  buf.append(',');
  }
  //deleteLastComma() 方法将移除缓冲区末尾最后一个逗号字符（如果有的话）
  private void deleteLastComma() {
  if (buf.length() > 0)
  if (buf.charAt(buf.length()-1) == ',')
  buf.deleteCharAt(buf.length()-1);
  }
  //startObject() 方法附加一个 { 字符，用于表示一个 JavaScript 对象的开始
  public void startObject() {
  buf.append('{');
  }
  //property() 方法编码 JavaScript 属性
  public void property(String name, Object value) {
  buf.append(name);
  buf.append(':');
  literal(value);
  comma();
  }
  //endObject() 方法附加一个 } 字符，用于表示一个 JavaScript 对象的结束
  public void endObject() {
  deleteLastComma();
  buf.append('}');
  comma();
  }
  //startArray() 方法附加一个 [ 字符，用于表示一个 JavaScript 数组的开始
  public void startArray() {
  buf.append('[');
  }
  //element() 方法编码 JavaScript 数组的元素
  public void element(Object value) {
  literal(value);
  comma();
  }
  //endArray() 方法附加一个 ] 字符，用于表示一个 JavaScript 数组的结束
  public void endArray() {
  deleteLastComma();
  buf.append(']');
  comma();
  }
  //toString() 方法返回 JSON 字符串
  public String toString() {
    deleteLastComma();
    return buf.toString();
  }
  //clear() 方法清空缓冲区
  public void clear() {
  buf.setLength(0);
  }
  //usage[用法]:DataModel 使用 JSONEncoder 类来编码其维护的数据
  /*
  public synchronized String getJSONString(Vector vctList) {
    JSONEncoder json = new JSONEncoder();
    json.startArray();
    for (int i = 0; i < vctList.size(); i++) {
      Hashtable hash = (Hashtable)vctList.get(i);
      json.startObject();
       Enumeration keys = hash.keys();
        while (keys.hasMoreElements()) {
          String key = (String) keys.nextElement();
          json.property(key, hash.get(key));
      }
      json.endObject();
    }
    json.endArray();
    return json.toString();
  }
*/
}
