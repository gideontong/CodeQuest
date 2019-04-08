# Open the input file
with open("Prob17.in.txt", "rt") as inputFile:
    # Read the number of test cases (trim out the newline)
    cases = int(inputFile.readline().replace("\n", ""))
    # For each test case
    for caseNum in range(cases):
        # Read the next line
        board = inputFile.readline().replace("\n", "")
        winner = '-'
        # Check for winning combinations
        if board[0] == 'X' or board[0] == 'O':
            if board[0] == board[1] and board[0] == board[2]:
                # Top horiz
                winner = board[0]
            elif board[0] == board[4] and board[0] == board[8]:
                # Downward diag
                winner = board[0]
            elif board[0] == board[3] and board[0] == board[6]:
                # Left vert
                winner = board[0]
        if winner == '-' and (board[1] == 'X' or board[1] == 'O'):
            if board[1] == board[4] and board[1] == board[7]:
                # Center vert
                winner = board[1]
        if winner == '-' and (board[2] == 'X' or board[2] == 'O'):
            if board[2] == board[5] and board[2] == board[8]:
                # Right vert
                winner = board[2]
            elif board[2] == board[4] and board[2] == board[6]:
                # Upward diag
                winner = board[2]
        if winner == '-' and (board[3] == 'X' or board[3] == 'O'):
            if board[3] == board[4] and board[3] == board[5]:
                # Middle horiz
                winner = board[3]
        if winner == '-' and (board[6] == 'X' or board[6] == 'O'):
            if board[6] == board[7] and board[6] == board[8]:
                # Bottom horiz
                winner = board[6]
        # re-print board, suppressing newlines
        print(board + " = ", end="")
		# print winner (if any)
        if winner == '-':
            print("TIE")
        else:
            print(winner + " WINS")
