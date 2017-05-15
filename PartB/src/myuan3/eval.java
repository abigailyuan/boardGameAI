private int evaluation(){
        int score = 0;
        int bonus = (myName == 'H')? 1 : 10;
        int numOriginDistance = boardHash.size()-1;
        ArrayList<Position> Positions = new ArrayList<>();
        /** check pieces */
        Set set;
        set = opponentHash.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            Position tmp = (Position) me.getValue();
            Positions.add(tmp);
        }

        set = myHash.entrySet();
        it = set.iterator();

//        System.out.printf("Board is \n\n");
//        for (int row = boardHash.size()-1; row>= 0;row--){
//            for (int col = 0; col<= boardHash.size()-1;col++){
//                System.out.printf("%c    ",boardHash.get(row).get(col));
//            }
//            System.out.printf("\n\n");
//        }

        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            Position tmp = (Position) me.getValue();
//            System.out.printf("my tmp is (%d, %d)\n", tmp.getRow(), tmp.getColumn());
//            System.out.printf("The Position is (%d,%d), the value on it is %c\n", ((Position) me.getValue()).getRow(), ((Position) me.getValue()).getColumn(), me.getKey());
//            System.out.printf("my finished is %d\n",4-myHash.size());
//            System.out.printf("oppo finished is %d\n",4-opponentHash.size());

            /** clone a new map */
            ArrayList<HashMap<Integer,Integer>> map = cloneMap(boardHash,myName);

            /** replace the original position with 0 */
            map.get(tmp.getRow()).replace(tmp.getColumn(),0);

//            System.out.printf("My name is %c\n",myName);

//            System.out.printf("Map is \n\n");
//            for (int row = map.size()-1; row>= 0;row--){
//                for (int col = 0; col<= map.size()-1;col++){
//                    System.out.printf("%d    ",map.get(row).get(col));
//                }
//                System.out.printf("\n");
//            }

            distanceToFinish(tmp,map,myName);
            score += 11*(numOriginDistance - distanceToFinish(tmp, map, myName));
            /** add bonus for exact block the piece
             *  H
             *  V
             *
             *  that H would get bonus
             *
             *  H V
             *
             *  that V would get bonus
             *
             */
            if (myName == 'H') {
                if (tmp.getRow()-1 >= 0 && boardHash.get(tmp.getRow()-1).get(tmp.getColumn()) == 'V'){
                    score += bonus;
//                    System.out.printf("tmp (%d,%d) Block 'V'!\n",tmp.getRow(),tmp.getColumn());
                }
            }
            else{
                if (tmp.getColumn()-1 >= 0 && boardHash.get(tmp.getRow()).get(tmp.getColumn()-1) == 'H'){
                    score += bonus;
                }
            }


            /** add finished piece score */
            /** calculate how many pieces has finished is to just check the edge of the winning side */
            if (distanceToFinish(tmp,map,myName) == 0){
                score += 5;
            }

//            System.out.printf("the Piece(%d,%d) %c minimum distance to finish is %d \n",tmp.getRow(),tmp.getColumn(),myName,distanceToFinish(tmp,map,myName));
//            System.out.printf("score is %d\n",score);
        }

        /** calculate the opponent score */
        set = opponentHash.entrySet();
        it = set.iterator();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            Position tmp = (Position) me.getValue();

            /** clone a new map */
            ArrayList<HashMap<Integer,Integer>> map = cloneMap(boardHash,oppoName);

            /** replace the original position with 0 */
            map.get(tmp.getRow()).replace(tmp.getColumn(),0);

            distanceToFinish(tmp,map,oppoName);
            score -= 6*(numOriginDistance - distanceToFinish(tmp, map, oppoName));

            /** deduct bonus for exact block the piece
             *  H V
             *
             *  that H would deduct bonus
             *
             */
            if (myName == 'H') {
                if (tmp.getColumn()+1 <= boardHash.size()-1 && boardHash.get(tmp.getRow()).get(tmp.getColumn()+1) == 'V'){
                    score -= bonus;
                }
            }
            else{
                if (tmp.getRow()+1 <= boardHash.size()-1 && boardHash.get(tmp.getRow()+1).get(tmp.getColumn()) == 'H'){
                    score -= bonus;
                }
            }
            if (distanceToFinish(tmp,map,oppoName) == 0){
                score -= 5;
            }

//            System.out.printf("the Piece(%d,%d) %c minimum distance to finish is %d \n",tmp.getRow(),tmp.getColumn(),oppoName,distanceToFinish(tmp,map,oppoName));
//            System.out.printf("score is %d\n",score);
        }
//        System.out.printf("the size of myhash is %d\n the size of oppo has is %d\n",myHash.size(),opponentHash.size());

//        System.out.printf("final score is %d\n",score);
        return score;
    }
