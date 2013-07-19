/*  WebGodot: Godot Server-Side Implementation. 
    Copyright (C) 2013  Giacomo Marciani <giacomo.marciani@gmail.com>.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
