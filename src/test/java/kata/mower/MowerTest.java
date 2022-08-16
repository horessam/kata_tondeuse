package kata.mower;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class MowerTest {

    private static final Path FILE_NAME = Path.of("src/test/java/resources/mowerInstructions.txt");
    private static final String RIGHT_ROTATION_COMMAND = "D";
    private static final String LEFT_ROTATION_COMMAND = "G";
    private static final String ADVANCE_COMMAND = "A";

    @Test
    public void should_initialize_mower1_Coordinate_with_1_2_N() throws IOException {
        // Given
        Mower mower = initializeMowerFromFile(1);

        // Then
        Assertions.assertEquals(1, mower.getCoordinate().x());
        Assertions.assertEquals(2, mower.getCoordinate().y());
        Assertions.assertEquals(Orientation.N, mower.getOrientation());
    }

    @Test
    public void should_initialize_mower2_Coordinate_with_3_3_E() throws IOException {
        // Given
        Mower mower = initializeMowerFromFile(2);

        // Then
        Assertions.assertEquals(3, mower.getCoordinate().x());
        Assertions.assertEquals(3, mower.getCoordinate().y());
        Assertions.assertEquals(Orientation.E, mower.getOrientation());
    }

    @Test
    public void mower1_ends_at_1_3_N_when_execute_advance_command() throws IOException {
        // Given
        Mower mower = initializeMowerFromFile(1);

        //When
        mower.execute(ADVANCE_COMMAND);

        //Then
        Assertions.assertEquals(1, mower.getCoordinate().x());
        Assertions.assertEquals(3, mower.getCoordinate().y());
        Assertions.assertEquals(Orientation.N, mower.getOrientation());
    }

    @Test
    public void mower1_ends_at_1_2_W_when_execute_left_rotation_command() throws IOException {
        // Given
        Mower mower = initializeMowerFromFile(1);

        //When
        mower.execute(LEFT_ROTATION_COMMAND);

        //Then
        Assertions.assertEquals(1, mower.getCoordinate().x());
        Assertions.assertEquals(2, mower.getCoordinate().y());
        Assertions.assertEquals(Orientation.W, mower.getOrientation());
    }

    @Test
    public void mower1_ends_at_1_2_E_when_execute_right_rotation_command() throws IOException {
        // Given
        Mower mower = initializeMowerFromFile(1);

        //When
        mower.execute(RIGHT_ROTATION_COMMAND);

        //Then
        Assertions.assertEquals(1, mower.getCoordinate().x());
        Assertions.assertEquals(2, mower.getCoordinate().y());
        Assertions.assertEquals(Orientation.E, mower.getOrientation());
    }

    @Test
    public void should_return_exception_when_command_does_not_exist() throws IOException {
        // Given
        Mower mower = initializeMowerFromFile(1);

        // Then
        Assertions.assertThrows(IllegalStateException.class, () -> mower.execute("Z"));
    }

    @Test
    public void mower1_ends_at_1_3_N_when_execute_sequence_command() throws IOException {
        // Given
        Mower mower = initializeMowerFromFile(1);
        String sequenceCommand = getMowerSequenceCommand(1);

        //When
        mower.execute(sequenceCommand);

        //Then
        Assertions.assertEquals(1, mower.getCoordinate().x());
        Assertions.assertEquals(3, mower.getCoordinate().y());
        Assertions.assertEquals(Orientation.N, mower.getOrientation());
    }

    @Test
    public void mower2_ends_at_5_1_E_when_execute_sequence_command() throws IOException {
        // Given
        Mower mower = initializeMowerFromFile(2);
        String sequenceCommand = getMowerSequenceCommand(2);

        //When
        mower.execute(sequenceCommand);

        //Then
        Assertions.assertEquals(5, mower.getCoordinate().x());
        Assertions.assertEquals(1, mower.getCoordinate().y());
        Assertions.assertEquals(Orientation.E, mower.getOrientation());
    }

    private Mower initializeMowerFromFile(int mowerIndex) throws IOException {
        Coordinate maxCoordinate = getMaxCoordinate();
        String[] parametersMowerLine = getMowerParameters(mowerIndex);
        // Parameters 1 & 2
        Coordinate coordinate = Coordinate.of(Integer.parseInt(parametersMowerLine[0]), Integer.parseInt(parametersMowerLine[1]));
        // Parameters 3
        Orientation orientation = Orientation.valueOf(parametersMowerLine[2]);

        return new Mower(coordinate, maxCoordinate, orientation);
    }

    private Coordinate getMaxCoordinate() throws IOException {
        // First line in file
        String[] maxCoordinateString = Files.readString(FILE_NAME).split("[\\r\\n]+")[0].split(" ");
        return Coordinate.of(Integer.parseInt(maxCoordinateString[0]), Integer.parseInt(maxCoordinateString[1]));
    }

    private String[] getMowerParameters(int mowerIndex) throws IOException {
        // Line mowerIndex * 2 - 1 in file
        return Files.readString(FILE_NAME).split("[\\r\\n]+")[mowerIndex * 2 - 1].split(" ");
    }

    private String getMowerSequenceCommand(int mowerIndex) throws IOException {
        // Line mowerIndex * 2 in file
        return Files.readString(FILE_NAME).split("[\\r\\n]+")[mowerIndex * 2];
    }
}
