import java.util.ArrayList;

public class Queen extends ChessPiece {
    Queen(int x, int y, ChessColor color) {
        super(x, y, color);
        this.asciiCharacter = (this.color == ChessColor.BLACK) ? '♛' : '♕';
        ArrayList<Polygon> polygons = new ArrayList<>();


        polygons.add(
                new Polygon(
                        new double[]{0.08, 0.904, 0.906, 0.08},
                        new double[]{0.94, 0.942, 0.8, 0.802},
                        4,
                        outline,
                        fill,
                        getX() * getSize(),
                        getY() * getSize(),
                        getSize())
        );

        polygons.add(
                new Polygon(
                        new double[]{0.172, 0.05, 0.212, 0.26, 0.356, 0.46, 0.548, 0.716, 0.708, 0.924, 0.792},
                        new double[]{0.744, 0.296, 0.536, 0.202, 0.51, 0.126, 0.5, 0.2, 0.504, 0.288, 0.742},
                        11,
                        outline,
                        fill,
                        getX() * getSize(),
                        getY() * getSize(),
                        getSize())
        );
        setPolygons(polygons);
    }

    @Override
    public boolean moveOk(Square[][] square, int oldX, int oldY, int newX, int newY) {
        if (oldX == newX || oldY == newY){
            return true;
        }
        for (int i_X = oldX, i_Y = oldY; i_X >=0 && i_X <= 8 && i_Y >= 0 && i_Y <= 8; ++i_X, ++i_Y){
            if(i_Y == newY && i_X == newX){
                return true;
            }
        }
        for (int i_X = oldX, i_Y = oldY; i_X >=0 && i_X <= 8 && i_Y >= 0 && i_Y <= 8; --i_X, --i_Y){
            if(i_Y == newY && i_X == newX){
                return true;
            }
        }
        for (int i_X = oldX, i_Y = oldY; i_X >=0 && i_X <= 8 && i_Y >= 0 && i_Y <= 8; ++i_X, --i_Y){
            if(i_Y == newY && i_X == newX){
                return true;
            }
        }
        for (int i_X = oldX, i_Y = oldY; i_X >=0 && i_X <= 8 && i_Y >= 0 && i_Y <= 8; --i_X, ++i_Y){
            if(i_Y == newY && i_X == newX){
                return true;
            }
        }
        return false;
    }

    @Override
    public char getFenRepresentation() {
        return (this.color == ChessColor.BLACK) ? 'q' : 'Q';
    }

    @Override
    public void drawHighlightedSquares(Square[][] square, int pieceX, int pieceY) {

    }
}

