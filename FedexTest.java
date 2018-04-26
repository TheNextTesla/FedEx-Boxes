import java.util.Arrays;
import java.util.Locale;

public class FedexTest 
{
	public static final double TRAILER_X = 53 * 12;
	public static final double TRAILER_Y = 8.5 * 12;
	public static final double TRAILER_Z = 8 * 12;
	
	public static final int NUMBER_OF_BOXES = 7500;
	
	public static final int[][] configurations = 
	{
			{20, 12, 16},
			{20, 16, 12},
			{16, 12, 20},
			{16, 20, 12},
			{12, 20, 16},
			{12, 16, 20},
	};
	
	public static void main(String[] args) 
	{
		double maxEfficiency = 0;
		int maxEfficiencyIndex = -1;
		double[] maxEfficiencyInfo = null;
		
		for(int i = 0; i < configurations.length; i++)
		{
			int[] configuration = configurations[i];
			double[] efficiencyInfo = calculateNumberOfTrailers(configuration[0], configuration[1], configuration[2]);
			
			System.out.println(String.format(Locale.US, "Attempt %d : %s : Efficiency %2.3f", (i + 1), Arrays.toString(configuration), efficiencyInfo[1]));
			
			if(efficiencyInfo[1] > maxEfficiency)
			{
				maxEfficiencyIndex = i;
				maxEfficiency = efficiencyInfo[1];
				maxEfficiencyInfo = efficiencyInfo;
			}
		}
		
		double remainderX  = maxEfficiencyInfo[2];
		double remainderY  = maxEfficiencyInfo[3];
		double remainderZ  = maxEfficiencyInfo[4];
		
		if(remainderX <= 12 && remainderY <= 12 && remainderZ <= 12)
		{
			System.out.println("A More Efficient Arrangement Does Not Exist");
		}
		
		System.out.println(String.format(Locale.US, "\nThe best arrangement is with the %d in side along the axis of the 53' side,"
				+ "\nthe %d side along the axis of the 8.5' side,"
				+ "\nand the %d side along the axis of the 8' side"
				+ "\nThis yields an efficiency (for all but the last one) of %3.4f and would fill %1.4f trucks entirely\n"
				+ "Thus %d trucks are needed to hold the 7500 packages, with %d in each (except the last) and %d in the last"
				+ "\nand the last truck will have an load factor of %3.4f", configurations[maxEfficiencyIndex][0], configurations[maxEfficiencyIndex][1],
				configurations[maxEfficiencyIndex][2], maxEfficiencyInfo[1], maxEfficiencyInfo[0], (int) Math.ceil(maxEfficiencyInfo[0]), (int) maxEfficiencyInfo[5],
				(int)(NUMBER_OF_BOXES - (Math.floor(maxEfficiencyInfo[0]) * maxEfficiencyInfo[5])), (maxEfficiencyInfo[0] % 1) * maxEfficiencyInfo[1]));
	}
	
	public static double[] calculateNumberOfTrailers(int x, int y, int z)
	{	
		int maxBoxesX = (int) Math.floor(TRAILER_X / x);
		int maxBoxesY = (int) Math.floor(TRAILER_Y / y);
		int maxBoxesZ = (int) Math.floor(TRAILER_Z / z);
		
		double remainderSpaceX = TRAILER_X - maxBoxesX * x;
		double remainderSpaceY = TRAILER_Y - maxBoxesY * y;
		double remainderSpaceZ = TRAILER_Z - maxBoxesZ * z;
		
		int maxBoxesPerTrailer = (maxBoxesX * maxBoxesY * maxBoxesZ);
		
		double numberOfTrailers = NUMBER_OF_BOXES * 1.0 / maxBoxesPerTrailer;
		
		double sizeOfMaxBoxesX = maxBoxesX * x;
		double sizeOfMaxBoxesY = maxBoxesY * y;
		double sizeOfMaxBoxesZ = maxBoxesZ * z;
		
		double totalVolumeUsed = sizeOfMaxBoxesX * sizeOfMaxBoxesY * sizeOfMaxBoxesZ;
		double totalVolumeAll = TRAILER_X * TRAILER_Y * TRAILER_Z;
		
		double efficiency = (totalVolumeUsed / totalVolumeAll);
		
		double[] returnInfo = {numberOfTrailers, efficiency, remainderSpaceX, remainderSpaceY, remainderSpaceZ, maxBoxesPerTrailer};
		
		return returnInfo;
	}
}
