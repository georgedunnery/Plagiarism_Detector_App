"""
George Dunnery
CS 5001
Homework 5 - Programming #1
11/2/2018
"""

# Import the required module to enable functions
import hanoi_viz

# Define the names for the three towers
NAME = ["Cthulu", "David", "Goliath"]

# Define the max and min heights for the hanoi towers
MAX = 8
MIN = 1

def move_tower(num_disks, source, target, helper, towers):
    """
    Parameters: The number of disks, integer. The source, target, and helper
    towers, all as strings. Dictionary of towers, which keeps track of which
    rings are on which tower
    Does: Recursively solves the hanoi puzzle: calls the move_disk function
    from hanoi_viz.py to move each disk, and calls itself to manipulate
    which tower is the source, helper, and target for each step. The rings must
    be moved one at a time, and each ring may only be on top of larger rings
    and below smaller ones. We only care about moving disks that exist, so the
    base case here is implicit - when there are no disks left, we don't need
    to move any disks!
    Returns: Nothing
    """
    if num_disks > 0:
        move_tower(num_disks - 1, source, helper, target, towers)
        hanoi_viz.move_disk(source, target, towers)
        move_tower(num_disks -1, helper, target, source, towers)
        
def main():

    # Prompt the user for the number of disks until valid input is given
    num_disks = 0
    while num_disks > MAX or num_disks < MIN:
        try:
            num_disks = int(input("Please enter an integer from 1 - 8:\n"))

        # Tell the CPU how to handle a ValueError when something other than
        # an integer is entered by the user
        except ValueError:
            print("Input is required to be an integer, please try again.")

    # Create the dictionary of towers using module's function
    towers = hanoi_viz.initialize_towers(num_disks, NAME[0], NAME[1], NAME[2])

    # Use a recursive function to move all rings from one tower to another
    move_tower(num_disks, NAME[0], NAME[1], NAME[2], towers)

    
main()

