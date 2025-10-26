package com.example.profitness.database;

import androidx.room.*;
import com.example.profitness.database.entities.StepData;
import com.example.profitness.database.entities.UserGoals;
import com.example.profitness.database.entities.WorkoutSession;

import java.util.List;

@Dao
public interface FitnessDao {

    // Step Data operations
    @Insert
    void insertStepData(StepData stepData);

    @Update
    void updateStepData(StepData stepData);

    @Query("SELECT * FROM step_data WHERE date = :date")
    StepData getStepDataByDate(String date);

    @Query("SELECT * FROM step_data ORDER BY date DESC LIMIT :limit")
    List<StepData> getRecentStepData(int limit);

    @Query("SELECT * FROM step_data ORDER BY date DESC")
    List<StepData> getAllStepData();

    @Query("SELECT SUM(stepCount) FROM step_data")
    int getTotalSteps();

    @Query("SELECT SUM(caloriesBurned) FROM step_data")
    double getTotalCalories();

    @Query("SELECT * FROM step_data WHERE date BETWEEN :startDate AND :endDate")
    List<StepData> getStepDataBetweenDates(String startDate, String endDate);

    @Query("DELETE FROM step_data WHERE date = :date")
    void deleteStepDataByDate(String date);

    // User Goals operations
    @Insert
    void insertUserGoals(UserGoals goals);

    @Update
    void updateUserGoals(UserGoals goals);

    @Query("SELECT * FROM user_goals WHERE id = 1")
    UserGoals getUserGoals();

    // Workout Session operations
    @Insert
    void insertWorkoutSession(WorkoutSession session);

    @Query("SELECT * FROM workout_sessions ORDER BY startTime DESC")
    List<WorkoutSession> getAllWorkoutSessions();
}