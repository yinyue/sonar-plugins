/*
Copyright (C) 2001 Chr. Clemens Lee <clemens@kclee.com>.

This file is part of JavaNCSS

JavaNCSS is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the
Free Software Foundation; either version 2, or (at your option) any
later version.

JavaNCSS is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License
along with JavaNCSS; see the file COPYING.  If not, write to
the Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA 02111-1307, USA.  */package org.codehaus.javancss.metrics;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Iterator;

import org.codehaus.javancss.JavaNcss;
import org.codehaus.javancss.Resource;
import org.codehaus.javancss.ResourceTreeBuilder;
import org.codehaus.javancss.Resource.Type;
import org.junit.Test;

public class ClassCounterTest {

	@Test
	public void analyseTest003() {
		Resource project = new JavaNcss(new File("target/test-classes/Test003.java")).analyseSources();
		Resource defaultPackage = project.getFirstChild();
		Resource file = defaultPackage.getFirstChild();
		assertEquals(3, file.getClasses());

		Iterator<Resource> classes = file.getChildren().iterator();
		Resource anotherClass = classes.next();
		assertEquals("AnotherCar", anotherClass.getName());
		assertEquals(Type.CLASS, anotherClass.getType());
		Resource carClass = classes.next();
		assertEquals("Car", carClass.getName());
		assertEquals(Type.CLASS, carClass.getType());

		Resource wheelClass = ResourceTreeBuilder.findSubChildren(carClass, Type.CLASS).get(0);
		assertEquals("Car#Wheel", wheelClass.getName());
		assertEquals(Type.CLASS, wheelClass.getType());
	}

}
