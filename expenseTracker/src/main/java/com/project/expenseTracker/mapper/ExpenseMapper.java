package com.project.expenseTracker.mapper;

import com.project.expenseTracker.dto.ExpenseDto;
import com.project.expenseTracker.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);
        List<ExpenseDto> mapToResponse(List<Expense> expense);

        ExpenseDto mapToResponse(Expense expense);


}
