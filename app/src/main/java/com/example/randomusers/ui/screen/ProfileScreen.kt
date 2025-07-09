package com.example.randomusers.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.randomusers.ui.viewmodel.ProfileViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.zIndex
import com.example.randomusers.ui.screen.components.ErrorDialog
import com.example.randomusers.ui.theme.PurpleGrey40
import com.example.randomusers.ui.theme.PurpleGrey80

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    userId: String?
) {
    val user by viewModel.user.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }

    LaunchedEffect(errorMessage) {
        if (!errorMessage.isNullOrEmpty()) {
            showErrorDialog = true
        }
    }

    if (showErrorDialog) {
        ErrorDialog(
            errorMessage = errorMessage,
            onDismiss = {
                showErrorDialog = false
                viewModel.clearError()
            }
        )
    }

    LaunchedEffect(userId) {
        userId?.let {
            viewModel.loadUser(it)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 40.dp)
                .verticalScroll(scrollState)
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(PurpleGrey80)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 18.dp)
                            .clip(RoundedCornerShape(50.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = user?.picture,
                            contentDescription = "Иконка пользователя",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(90.dp)
                        )
                    }

                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = user?.name.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = "@${user?.login}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 18.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(PurpleGrey40)
                                .padding(6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            ContactCard(
                                user?.phone, Icons.Default.Phone, "Позвонить"
                            ) { user?.phone?.let { viewModel.makePhoneCall(context, it) } }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(PurpleGrey40)
                                .padding(6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            ContactCard(
                                user?.coordinates,
                                Icons.Default.LocationOn,
                                "Посмотреть на карте"
                            )
                            { user?.coordinates?.let { viewModel.openMap(context, it) } }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(PurpleGrey40)
                                .padding(6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            ContactCard(
                                user?.email,
                                Icons.Default.Email,
                                "Написать"
                            ) { user?.email?.let { viewModel.sendEmail(context, it) } }

                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(PurpleGrey80)
                    .padding(12.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    InfoText(user?.gender, "Пол")
                    InfoText(user?.let { "${it.birthDay} (${it.age} лет)" }, "Дата рождения")
                    InfoText(user?.nat, "Национальность")
                    InfoText(user?.registered, "Дата регистрации")
                    InfoText(user?.address, "Адрес", onClick = user?.address?.let { viewModel.openMap(context, it) })
                    InfoText(user?.postcode, "Почтовый индекс")
                    InfoText(user?.cell, "Мобильный телефон")
                    InfoText(user?.timezone, "Часовой пояс", isDividerNeed = false)

                }
            }
        }
    }
}

@Composable
fun InfoText(text: String?, textTitle: String, onClick: Unit? = null, isDividerNeed: Boolean = true) {
    if (text != null) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier.clickable(onClick = { onClick })
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Box(
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = textTitle,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = PurpleGrey40,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (isDividerNeed) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = PurpleGrey40
            )
        }
    }
}

@Composable
fun ContactCard(
    text: String?,
    icon: ImageVector,
    actionText: String,
    action: (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    val clipboardManager = LocalClipboardManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { expanded = true }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Иконка контакта",
            modifier = Modifier.size(24.dp),
            tint = PurpleGrey80
        )
        Text(
            text = text.toString(),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Normal,
            color = PurpleGrey80,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .zIndex(1f)
            .background(Color.White)
    ) {
        DropdownMenuItem(
            onClick = {
                if (action != null) {
                    action()
                }
                expanded = false
            },
            text = {
                Text(
                    "$actionText $text",
                    color = PurpleGrey40
                )
            })
        DropdownMenuItem(
            onClick = {
                text?.let { AnnotatedString(it) }?.let { clipboardManager.setText(it) }
                expanded = false
            },
            text = {
                Text(
                    "Скопировать $text",
                    color = PurpleGrey40
                )
            })
    }
}