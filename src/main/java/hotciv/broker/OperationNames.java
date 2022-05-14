package hotciv.broker;

public class OperationNames {
    public static final String GAME_PREFIX = "game";
    public static final String CITY_PREFIX = "city";
    public static final String TILE_PREFIX = "tile";
    public static final String UNIT_PREFIX = "unit";

    //Separator
    public static final String SEPARATOR = "-";

    public static final String GAME_GET_WINNER = GAME_PREFIX + SEPARATOR + "get_winner";
    public static final String GAME_GET_AGE = GAME_PREFIX + SEPARATOR + "get_age";
    public static final String GAME_GET_CITY = GAME_PREFIX + SEPARATOR + "get_city_at";
    public static final String GAME_GET_PLAYER_IN_TURN = GAME_PREFIX + SEPARATOR + "get_player_in_turn";
    public static final String GAME_MOVE_UNIT = GAME_PREFIX + SEPARATOR + "move_unit";
    public static final String GAME_END_TURN = GAME_PREFIX + SEPARATOR + "end_turn";
    public static final String GAME_PERFORM_ACTION = GAME_PREFIX + SEPARATOR + "perform_action";
    public static final String GAME_CHANGE_WORKFORCE = GAME_PREFIX + SEPARATOR + "change_workforce";
    public static final String GAME_CHANGE_PRODUCTION = GAME_PREFIX + SEPARATOR + "change_production";
    public static final String GAME_SET_TILE_FOCUS = GAME_PREFIX + SEPARATOR + "set_tile_focus";
    public static final String GAME_GET_UNIT = GAME_PREFIX + SEPARATOR + "get_unit_at";
    public static final String GAME_GET_TILE = GAME_PREFIX + SEPARATOR + "get_tile_at";

    public static final String UNIT_GET_TYPE = UNIT_PREFIX + SEPARATOR + "get_type";
    public static final String UNIT_GET_OWNER = UNIT_PREFIX + SEPARATOR + "get_owner";
    public static final String UNIT_GET_MOVE_COUNT = UNIT_PREFIX + SEPARATOR + "get_move_count";
    public static final String UNIT_GET_DEFENCE = UNIT_PREFIX + SEPARATOR + "get_defence";
    public static final String UNIT_GET_ATTACK = UNIT_PREFIX + SEPARATOR + "get_attack";

    public static final String CITY_GET_OWNER = CITY_PREFIX + SEPARATOR + "get_owner";
    public static final String CITY_GET_SIZE = CITY_PREFIX + SEPARATOR + "get_size";
    public static final String CITY_GET_TREASURY = CITY_PREFIX + SEPARATOR + "get_treasury";
    public static final String CITY_GET_PRODUCTION = CITY_PREFIX + SEPARATOR + "get_production";
    public static final String CITY_GET_WORKFORCEFOCUS = CITY_PREFIX + SEPARATOR + "get_workforcefocus";

    public static final String TILE_GET_TYPE_STRING = TILE_PREFIX + SEPARATOR + "get_type_string";
}
