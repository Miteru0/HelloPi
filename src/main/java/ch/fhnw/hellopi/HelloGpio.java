package ch.fhnw.hellopi;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloGpio {

	private static final Logger logger = LoggerFactory.getLogger(HelloGpio.class);
	private static final boolean[] toBlink = new boolean[1];

	// Connect a LED to PIN 22, a button to PIN 24
	private static final int PIN_LED = 22;
	private static final int PIN_BTN = 24;

	public static void main(String[] args) throws InterruptedException {
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

		final Context pi4j = Pi4J.newAutoContext();

		// here we will create I/O interface for a (GPIO) digital output pin with default settings.
		final DigitalOutput led = pi4j.dout().create(PIN_LED);

		// here we will create I/O interface for a (GPIO) digital input pin using a DigitalInputConfig and individually settings
		final DigitalInputConfig buttonConfig = DigitalInput.newConfigBuilder(pi4j)
				.id("BCM_" + PIN_BTN)
				.name("Button")
				.address(PIN_BTN)
				.pull(PullResistance.PULL_DOWN)
				.debounce(3000L)
				.build();  //don't forget to build the config
		DigitalInput button = pi4j.create(buttonConfig);

		//Get informed whenever the button state changes
		button.addListener(e -> {
			switch (e.state()) {
				case HIGH    -> {
                    logger.info("Button was pressed!");
                }
				case LOW     -> {
                    logger.info("Button was depressed!");
					toBlink[0] ^= true;
					if (toBlink[0]) {
						led.high();
					}
					else {
						led.low();
					}
					
                }
				case UNKNOWN -> logger.info("Something unknown happened!!");
			}
		});

		// cleanup on shutdown
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				logger.info("Shutting down...");
				pi4j.shutdown();
			} catch (Exception e) {
				logger.severe("Failed to shutdown");
			}
		}));

		//do what you need to do
		blink(led);
		logger.info("Press the button to see it in action!");


		// wait until program gets terminated
		synchronized (Thread.currentThread()) {
			Thread.currentThread().wait();
		}
	}

	private static void blink(DigitalOutput led){
		led.low();

		for (int i = 0; i < 6; i++) {
			led.high();
			sleep(200L);
			led.low();
			sleep(200L);
		}
	}

	private static void sleep(long msec) {
        try {
			Thread.currentThread().sleep(msec);
        } catch (InterruptedException e) {
			Thread.currentThread().interrupt();
        }
    }
}
