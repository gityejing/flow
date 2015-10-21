package com.maven.flow.util;
//JSONEncoder ���ṩ�˱������֡����������ķ���������洢�� java.lang.StringBuilder ��
/*���� String[ALL],StringBuffer[ALL],StringBuilder[JDK1.50]���ٶ�
StringBuilder>StringBuffer>String;
��ִ�е���String ����ʱ,��String���ǱȽϿ��
*/
public class JSONEncoder {
  private StringBuilder buf;
  //private StringBuffer buf;
  public JSONEncoder() {
    buf = new StringBuilder();
    //buf = new StringBuffer();
  }
 //character() �������뵥һ�ַ�
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
 //string() �������������ַ���
  public void string(String str) {
  int length = str.length();
  for (int i = 0; i < length; i++)
  character(str.charAt(i));
  }
  //literal() �������� JavaScript ����
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
  //comma() ��������һ�������ַ�
  private void comma() {
  buf.append(',');
  }
  //deleteLastComma() �������Ƴ�������ĩβ���һ�������ַ�������еĻ���
  private void deleteLastComma() {
  if (buf.length() > 0)
  if (buf.charAt(buf.length()-1) == ',')
  buf.deleteCharAt(buf.length()-1);
  }
  //startObject() ��������һ�� { �ַ������ڱ�ʾһ�� JavaScript ����Ŀ�ʼ
  public void startObject() {
  buf.append('{');
  }
  //property() �������� JavaScript ����
  public void property(String name, Object value) {
  buf.append(name);
  buf.append(':');
  literal(value);
  comma();
  }
  //endObject() ��������һ�� } �ַ������ڱ�ʾһ�� JavaScript ����Ľ���
  public void endObject() {
  deleteLastComma();
  buf.append('}');
  comma();
  }
  //startArray() ��������һ�� [ �ַ������ڱ�ʾһ�� JavaScript ����Ŀ�ʼ
  public void startArray() {
  buf.append('[');
  }
  //element() �������� JavaScript �����Ԫ��
  public void element(Object value) {
  literal(value);
  comma();
  }
  //endArray() ��������һ�� ] �ַ������ڱ�ʾһ�� JavaScript ����Ľ���
  public void endArray() {
  deleteLastComma();
  buf.append(']');
  comma();
  }
  //toString() �������� JSON �ַ���
  public String toString() {
    deleteLastComma();
    return buf.toString();
  }
  //clear() ������ջ�����
  public void clear() {
  buf.setLength(0);
  }
  //usage[�÷�]:DataModel ʹ�� JSONEncoder ����������ά��������
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
