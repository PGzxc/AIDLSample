// IMyAidl.aidl
package com.example.aidlsample;
import com.example.aidlsample.bean.Person;
interface IMyAidl{
 void addPerson(in Person person);
 List<Person> getPersonList();
}
