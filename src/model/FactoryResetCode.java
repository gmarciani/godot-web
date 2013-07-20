/*  WebGodot: Godot Server-Side Implementation. 
    
   Copyright 2013 Giacomo Marciani <giacomo.marciani@gmail.com>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.   
 */

package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;


public final class FactoryResetCode {
	
	private static FactoryResetCode singletonFactoryResetCode;

	private FactoryResetCode() {}

	public static synchronized FactoryResetCode getInstance() {
		if (singletonFactoryResetCode == null) {
			singletonFactoryResetCode = new FactoryResetCode();
			return singletonFactoryResetCode;
		}
		
		return singletonFactoryResetCode;
	}
	
	public ResetCode createResetCode() {
		Random randomGenerator = new Random();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return new ResetCode(randomGenerator.nextInt(Integer.MAX_VALUE) + 1, calendar.getTime());			
	}

}
