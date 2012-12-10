package es.dani.interactivedata.exercise2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Component;
import java.awt.Container;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author d.gutierrez.diez
 * 
 *         Test class for the Collector class
 * 
 */
public class CollectorTest {

	/**
	 * button field
	 */
	private JButton button;

	/**
	 * collector to be tested
	 */
	private Collector collector;
	/**
	 * componentList returned from the Collector.collectComponents method
	 */
	private List<Component> componentList;
	/**
	 * container where the components are going to be added
	 */
	private Container container;
	/**
	 * scroll bar field
	 */
	private JScrollBar scrollBar1;
	/**
	 * scroll bar field
	 */
	private JScrollBar scrollBar2;

	/**
	 * Setup method: fields are initialized
	 */
	@Before
	public void setUp() {
		collector = new Collector();
		container = new JPanel();
		button = new JButton();
		container.add(button);
		scrollBar1 = new JScrollBar();
		container.add(scrollBar1);
		scrollBar2 = new JScrollBar();
		container.add(scrollBar2);
	}

	/**
	 * test the collectComponents method and expect one object of the type
	 * JButton (added in the setUp)
	 */
	@Test
	public void test1Button() {

		componentList = collector.collectComponents(container, button);
		assertNotNull(componentList);
		assertEquals(1, componentList.size());

	}

	/**
	 * test the collectComponents method and expect two objects of the type
	 * JScrollBar (added in the setUp)
	 */
	@Test
	public void test2ScrollBars() {

		componentList = collector.collectComponents(container, scrollBar1);
		assertNotNull(componentList);
		assertEquals(2, componentList.size());

	}

	/**
	 * test the collectComponents method with both parameters set to null
	 */
	@Test
	public void testComponentAndContainerNull() {
		container = null;
		componentList = collector.collectComponents(container, null);
		assertNotNull(componentList);
		assertEquals(0, componentList.size());
	}

	/**
	 * test the collectComponents method if the object parameter is null
	 */
	@Test
	public void testObjectNull() {
		componentList = collector.collectComponents(container, null);
		assertNotNull(componentList);
		assertEquals(0, componentList.size());
	}

	/**
	 * test the collectComponents method - focus on sub containers
	 */
	@Test
	public void testSubContainers() {

		/* add sub containers information */
		Container container2 = new JPanel();
		Container container3 = new JPanel();
		Container container4 = new JPanel();
		Component scrollBar3 = new JScrollBar();
		Component scrollBar4 = new JScrollBar();

		container2.add(scrollBar3);
		container2.add(scrollBar4);

		container3.add(button);

		container2.add(container3);

		container.add(container2);

		container.add(container4);

		/*
		 * 4 components of the type JScrollBar (2 in container1 and 2 in
		 * container2)
		 */
		assertNotNull(collector.collectComponents(container, scrollBar1));
		assertEquals(4, collector.collectComponents(container, scrollBar1)
				.size());

		/*
		 * three components of the type container (sub containers)
		 */
		assertNotNull(collector.collectComponents(container, container2));
		assertEquals(3, collector.collectComponents(container, container2)
				.size());

		/*
		 * the button exist in the container3 (deleted from container1), so 1
		 * components of the type JButton
		 */
		assertNotNull(collector.collectComponents(container, button));
		assertEquals(1, collector.collectComponents(container, button).size());

	}

	/**
	 * test that is not possible to has a continuos loop
	 */
	@Test
	public void testUnendingLoop() {
		boolean failed = false;

		try {
			Container container2 = new JPanel();
			Container container3 = new JPanel();

			container.add(container2);
			container2.add(container3);
			container3.add(container);

			collector.collectComponents(container2, container);
		} catch (IllegalArgumentException ex) {
			failed = true;
		}
		Assert.assertTrue(failed);
	}

}
