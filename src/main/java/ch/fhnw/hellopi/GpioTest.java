package ch.fhnw.hellopi;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class GpioTest {

	private static final Logger logger = LoggerFactory.getLogger(GpioTest.class);

	// Connect a LED to PIN 22, a button to PIN 24
	private static final int PIN_LED = 22;
	private static final int PIN_BTN = 24;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		java.util.logging.Logger logger = java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);

		// ------------------------------------------------------------
		// Initialize the Pi4J Runtime Context
		// ------------------------------------------------------------
		// Before you can use Pi4J you must initialize a new runtime
		// context.
		//
		// The 'Pi4J' static class includes a few helper context
		// creators for the most common use cases.
		//
		// 'newAutoContext()' will automatically load all available Pi4J
		// extensions found in the application's classpath which
		// may include 'Platforms' and 'I/O Providers'

		// to get this default context you can use:

		final Context pi4j = Pi4J.newAutoContext();

		// Here we will create I/O interfaces for a (GPIO) digital output pin.
		final DigitalOutput led = pi4j.dout().create(PIN_LED);

		// Here we will create I/O interfaces for a (GPIO) digital input pin using a DigitalInputConfig
		//
		final DigitalInputConfig buttonConfig = DigitalInput.newConfigBuilder(pi4j)
				.id("BCM_" + PIN_BTN)
				.name("Button")
				.address(PIN_BTN)
				.pull(PullResistance.PULL_DOWN)
				.debounce(3000L)
				.build();  //don't forget to build the config

		DigitalInput button = pi4j.create(buttonConfig);

		button.addListener(e -> {
			switch (e.state()) {
				case HIGH    -> {
                    logger.info("Button was pressed!");
					led.low();
                }
				case LOW     -> {
                    logger.info("Button was depressed!");
					led.high();
                }
				case UNKNOWN -> logger.info("Something unknown happened!!");
				default      -> logger.info("if something else happens, it's a bug in Pi4J, this is the state '" + e.state() + "'");
			}
		});

		led.low();
		led.high();
		Thread.sleep(2000L);
		led.low();

		logger.info("Press the button to see it in action!");

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                logger.info("Shutting down...");
                pi4j.shutdown();
            } catch (Exception e) {
                logger.severe("Failed to shutdown");
            }
        }));

		synchronized (Thread.currentThread()) {
			Thread.currentThread().wait();
		}
	}
}
