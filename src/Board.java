import ecs100.UI;
import java.util.HashMap;
import java.awt.*;
import java.util.Optional;
public class Board {
    static Color darkSquareColor = new Color(119, 149, 86);
    static Color lightSquareColor = new Color(235, 236, 208);
    public Square[][] board;

    private static final int NUM_ROWS = 8;
    private static final double BOARD_WIDTH = 400;
    private static final double SQUARE_SIZE = BOARD_WIDTH / NUM_ROWS;

    public Optional<ChessPiece> selectedPiece = Optional.empty();

    /**
     * Creates a new board, populated with alternating colour squares.
     * Additionally, sets the size of ChessPiece to its SQUARE_SIZE
     */
    public Board() {
        this.board = new Square[NUM_ROWS][NUM_ROWS];
        ChessPiece.setSize(SQUARE_SIZE);

        for (int y = 0; y< NUM_ROWS; y++) {
            for (int x = 0; x< NUM_ROWS; x++) {
                Color squareColor = (y % 2 == x % 2) ? lightSquareColor : darkSquareColor;
                this.board[y][x] = new Square(Optional.empty(), squareColor, x, y, SQUARE_SIZE);
            }
        }
        boardFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    /**
     * Populates the board's squares with pieces according to Forsyth–Edwards Notation.
     * More info in <a href="https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation">Wikipedia</a>.
     * @param FEN A string showing the board position in Forsyth–Edwards Notation
     */
    public void boardFromFEN(String FEN) {
        int rank_num = 0;
        int file_num = 0;
        for (String rank : FEN.split("/")) {
            file_num = 0;
            for (Character info : rank.toCharArray()) {
                if (Character.isDigit(info)) {
                    file_num += Character.digit(info, 10);
                    continue;
                }
                board[rank_num][file_num].piece = Optional.of(charToChessPiece(info, file_num, rank_num));
                file_num++;
            }
            rank_num++;
        }
    }

    /**
     *
     * @param character One of  p, r, n, b, q, or k. May be uppercase or lowercase.
     * @param x Rank fo the piece
     * @param y File of the piece
     * @return The ChessPiece as specified by the notation
     * @throws IllegalStateException Throws if an invalid char is given
     */
    public ChessPiece charToChessPiece(char character, int x, int y) throws IllegalStateException {
        // Pick colour based on case
        ChessColor pieceColor = (Character.isUpperCase(character)) ? ChessColor.WHITE : ChessColor.BLACK;

        // Create and return ChessPiece
        return switch (Character.toLowerCase(character)) {
            case 'p' -> new Pawn(x, y, pieceColor);
            case 'r' -> new Rook(x, y, pieceColor);
            case 'n' -> new Knight(x, y, pieceColor);
            case 'b' -> new Bishop(x, y, pieceColor);
            case 'q' -> new Queen(x, y, pieceColor);
            case 'k' -> new King(x, y, pieceColor);
            default -> throw new IllegalStateException(character + ": is not a valid chess piece");
        };

    }

    /** Draw the board to the screen Square by Square.
     */
    public void draw() {
        for (int y = 0; y< NUM_ROWS; y++) {
            for (int x = 0; x< NUM_ROWS; x++) {
                // We pass the current selected piece so it's highlighted
                this.board[y][x].draw(this.selectedPiece);
            }
        }
    }

    /**
     * Returns the piece that the user clicks on.
     * @param mouseX The x coordinate of the mouse.
     * @param mouseY The y coordinate of the mouse.
     * @return An optional which contains the piece if the square the user clicked on had one
     */
    public Optional<ChessPiece> getChessPieceFromMouseSquare(double mouseX, double mouseY) {
        // Converts the mouse coordinate into a chess board coordinate.
        int x = (int) Math.floor(mouseX / SQUARE_SIZE);
        int y = (int) Math.floor(mouseY / SQUARE_SIZE);

        System.out.println(x+", "+y);

        // Return piece at that position, or empty Optional if outside board.
        if (x < NUM_ROWS && y < NUM_ROWS) {
            return board[y][x].piece;
        }
        return Optional.empty();
    }
}

class Square {
    public Optional<ChessPiece> piece;
    public Color color;
    int x;
    int y;
    double squareSize;

    public Square(Optional<ChessPiece> piece, Color color, int x, int y, double squareSize) {
        this.piece = piece;
        this.color = color;
        this.x = x;
        this.y = y;
        this.squareSize = squareSize;
    }

    /**
     * Draws this square. Additionally, draws piece if present.
     * @param highlightedPiece Highlight this square's piece if it's the same as this one
     */
    public void draw(Optional<ChessPiece> highlightedPiece) {
        // Draw square
        UI.setColor(color);
        UI.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);

        // Draw piece, highlighted if it matches arg
        if (piece.isPresent()) {
            ChessPiece currentPiece = piece.get();
            if (piece == highlightedPiece) {
                currentPiece.draw(Color.decode("#ffc90e"));
            } else {
                currentPiece.draw();
            }
//            System.out.print(" " + currentPiece.asciiCharacter);
        }
//        else {
//            System.out.print(" " + ((x%2==y%2)? '⬜' : '⬛'));
//        }
    }

}
