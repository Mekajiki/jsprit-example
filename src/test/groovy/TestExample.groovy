import jsprit.core.algorithm.VehicleRoutingAlgorithm
import jsprit.core.algorithm.box.Jsprit
import jsprit.core.problem.Location
import jsprit.core.problem.VehicleRoutingProblem
import jsprit.core.problem.job.Service
import jsprit.core.problem.solution.VehicleRoutingProblemSolution
import jsprit.core.problem.vehicle.VehicleImpl
import jsprit.core.problem.vehicle.VehicleType
import jsprit.core.problem.vehicle.VehicleTypeImpl
import jsprit.core.util.Solutions
import org.junit.Test

class TestExample {
  @Test
  void successWithOfficialExample() {
    /*
     *  Define vehicle
     */
    //define index of weight capacity
    final int WEIGHT_INDEX = 0

    //define vehicle type with weight capacity 2
    VehicleTypeImpl.Builder vehicleTypeBuilder =
      VehicleTypeImpl.Builder.newInstance('vehicleType')
      .addCapacityDimension(WEIGHT_INDEX, 2)
    VehicleType vehicleType = vehicleTypeBuilder.build()

    //define vehicle
    VehicleImpl.Builder vehicleBuilder = VehicleImpl.Builder.newInstance('vehicle')
    vehicleBuilder.setStartLocation(Location.newInstance(10, 10))
    vehicleBuilder.setType(vehicleType)
    VehicleImpl vehicle = vehicleBuilder.build()

    /*
     *  Define deliveries
     */
    Service service1 =
      Service.Builder.newInstance('1')
        .addSizeDimension(WEIGHT_INDEX, 1)
        .setLocation(Location.newInstance(5, 7))
        .build()

    Service service2 =
      Service.Builder.newInstance('2')
        .addSizeDimension(WEIGHT_INDEX, 1)
        .setLocation(Location.newInstance(5, 13))
        .build()

    Service service3 =
      Service.Builder.newInstance('3')
        .addSizeDimension(WEIGHT_INDEX, 1)
        .setLocation(Location.newInstance(15, 7))
        .build()

    Service service4 =
      Service.Builder.newInstance('4')
        .addSizeDimension(WEIGHT_INDEX, 1)
        .setLocation(Location.newInstance(15 ,13))
        .build()

    /*
     *  define a vehicle routing problem
     */
    VehicleRoutingProblem.Builder problemBuilder = VehicleRoutingProblem.Builder.newInstance()
    problemBuilder.addVehicle(vehicle)
    problemBuilder
      .addJob(service1)
      .addJob(service2)
      .addJob(service3)
      .addJob(service4)
    VehicleRoutingProblem problem = problemBuilder.build()

    /*
     * get the algorithm out-of-the-box
     */
    VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(problem)

    /*
     *  search a solution
     */
    Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions()

    /*
     *  use the static helper-method to get the best solution
     */
    VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions)

    /*
     *  test cost
     */
    assert bestSolution.cost == 35.3238075793812
  }
}
