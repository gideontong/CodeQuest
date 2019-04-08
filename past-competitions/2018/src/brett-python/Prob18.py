# Define a new class to represent a route
class DisneyRoute:
    """A class representing a route taken through DisneyWorld
    using one or more paths. DisneyRoutes know how long their
    routes are, what paths were taken, and where you'd end up
    at if you'd taken that route. DisneyRoutes are sortable
    by their length (shortest first) then by the paths taken
    (lowest numbered first)."""

    # Constructor function. Providing another DisneyRoute as the argument
    # will create a copy of that DisneyRoute (which we'll do in most cases)
    def __init__(self, original=None):
        self.paths = []
        self.rides = {'Start'}
        if original:
            self.location = original.location
            self.length = original.length
            self.sortValue = original.sortValue
            if len(original.paths) > 0:
                for path in original.paths[:]:
                    self.paths.append(path)
            for ride in original.rides:
                self.rides.add(ride)
        else:
            self.location = 'Start'
            self.length = 0
            self.sortValue = 0.0

    # Function to "walk" down another path, adding it to the route
    def add(self, path, addedLength, newLocation):
        self.paths.append(path)
        self.rides.add(newLocation)
        self.location = newLocation
        self.length = self.length + addedLength
        # Sort value is a combination of length and paths followed
        # The pre-decimal value is the length
        self.sortValue = self.sortValue + addedLength
        # After the decimal are the paths, in order
        self.sortValue = self.sortValue + (path * (10 ** -len(self.paths)))
        # So a route that's 12 minutes long using paths 1, 2, 4, 6
        # would have a sortValue of 12.1246

# Open the input file
with open("Prob18.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the next line
        data = inputFile.readline().replace("\n", "")
        # Split into path lengths
        splitData = data.split(" ")
        pathLengths = [-1]
        for item in splitData:
            pathLengths.append(int(item))
        # Create a list of DisneyRoutes; add a new "blank" route
        routes = []
        routes.append(DisneyRoute())
        # Loop until we find an answer
        answer = None
        while answer == None:
            # Get the best (shortest) route we currently have
            currentRoute = routes.pop(0)
            if len(currentRoute.rides) == 5:
                # If it's visited all four rides (plus start), this
                # is our answer.
                answer = currentRoute
                break
            elif len(currentRoute.paths) == 7:
                # The most paths a route can include is 7.
                # Beyond that, there must be a shorter route
                # (using the map we have). If we hit 7 paths
                # before we hit all rides, give up on the route.
                continue
            else:
                # Determine what paths we can take from
                # the route's current location. Set up a
                # dictionary to accept paths and destinations
                options = {}
                if currentRoute.location == "Start":
                    options[1] = "Pirates"
                    options[3] = "Splash"
                    options[5] = "Dwarves"
                    options[7] = "Space"
                elif currentRoute.location == "Pirates":
                    options[1] = "Start"
                    options[2] = "Splash"
                elif currentRoute.location == "Splash":
                    options[2] = "Pirates"
                    options[3] = "Start"
                    options[4] = "Dwarves"
                elif currentRoute.location == "Dwarves":
                    options[4] = "Splash"
                    options[5] = "Start"
                    options[6] = "Space"
                else:
                    options[6] = "Dwarves"
                    options[7] = "Start"
                # For each path option...
                for path, ride in options.items():
                    # Make a copy of the current route...
                    newPath = DisneyRoute(currentRoute)
                    # Have it travel down the path...
                    newPath.add(path, pathLengths[path], ride)
                    # Then add it to the list
                    routes.append(newPath)
                # Once they're all added, sort the list
                # using the routes' sort value
                # 'lambda route: route.sortValue' instructs Python
                # to sort on the 'sortValue' attribute of each 'route'
                routes.sort(key=lambda route: route.sortValue)
            # end if/else chain
        # end while answer == None
        # Print out the paths taken by the route
        first = True
        for path in answer.paths:
            # Add spaces as needed
            if not first:
                print(" ", end="")
            first = False
            print(str(path), end="")
        # Print the newline (specifying end above suppressed them)
        print("")
                
