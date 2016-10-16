package pl.codeme.jse.xogame.engine;

/**
 * Wyjątek w grze
 * 
 * @author pawel.apanasewicz@codeme.pl
 *	@author szymonskura@gmail.com
 */
@SuppressWarnings("serial")
public class GameOXException extends Exception {

    /**
     * Typ wyliczeniowy związany z możliwymi wyjątkami w grze
     * 
     * @author pawel.apanasewicz@codeme.pl
     *
     */
    public enum Error { 
        ERROR_EXCEPTION,
        APP_WRONG_COORDINATE_ERROR("Błędny koordynat planszy!"),
        APP_NOT_EMPTY_FIELD_ERROR("Pole o podanych kordynatach jest już zajęte!");

        /**
         * Komunikat dla wyjątku
         */
        private String message;

        /**
         * Domyślny konstruktor
         */
        private Error() { }

        /**
         * Konstruktor z komunikatem
         * 
         * @param message Komunikat dla wyjątku
         */
        private Error(String message) {
            this.message = message;
        }

        /**
         * @return komunikat dla wyjątku
         */
        public String getMessage() {
            return message;
        }

    }

    /**
     * Typ błedu z typu wyliczeniowego
     */
    @SuppressWarnings("unused")
    private Error error;

    /**
     * Konstruktor wyjątku
     * 
     * @param error Rodzaj błędu
     */
    public GameOXException(Error error) {
        super(error.getMessage());
        this.error = error;
    }

    /**
     * Konstruktor klsy
     * 
     * @param error Rodzaj błędu
     * @param cuse Przyczyna wyjątku
     */
    public GameOXException(Error error, Throwable cuse) {
        super(cuse.getMessage(), cuse);
        this.error = error;
    }

}
